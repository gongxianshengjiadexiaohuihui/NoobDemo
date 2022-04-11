package com.ggp.noob.demo.deadletter.config;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import sun.reflect.annotation.AnnotationParser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author Created by gongguanpeng on 2022/3/24 18:19
 */
@Component
public class RabbitListenerAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final ConcurrentMap<Class<?>,TypeMetadata> typeCache = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        final TypeMetadata metadata = this.typeCache.computeIfAbsent(targetClass, this::buildMetadata);
        for (ListenerMethod lm : metadata.listenerMethods) {
            for (RabbitListener rabbitListener : lm.annotations) {
                configDeadLetterQueue(rabbitListener);
            }
        }
        return bean;
    }
    /**
     * 配置死信队列
     * @param rabbitListener
     */
    private void configDeadLetterQueue(RabbitListener rabbitListener) {
        QueueBinding[] bindings = rabbitListener.bindings();
        QueueBinding binding = bindings[0];
        Queue queue = binding.value();
        Argument[] arguments = queue.arguments();
        Argument[] replace = configDeadLetterArgument(arguments);
        //出于某些技术原因，Java 虚拟机使用的“真实”注释类的实例是动态代理的实例。
        //Java 注解有一个名为 memberValues 的私有Map，其中存储了属性名称和属性值的k-v对。
        //但是使用spring的AnnotationUtils会转为SynthesizedAnnotationInvocationHandler实现，属性名称和属性值存在在缓存valueCache中
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(queue);
        Field f = null;
        try {
            f = invocationHandler.getClass().getDeclaredField("valueCache");
        } catch (NoSuchFieldException e) {
            //不会发生
        }
        f.setAccessible(true);
        Map<String,Object> memberValues = null;
        try {
            memberValues = (Map<String,Object>)f.get(invocationHandler);
        } catch (IllegalAccessException e) {
           //不会发生
        }
        memberValues.put("arguments",replace);
    }

    private Argument[] configDeadLetterArgument(Argument[] arguments) {
        Argument[] replace = new Argument[arguments.length+2];
        //转移旧数据
        System.arraycopy(arguments,0,replace,0,arguments.length);
        for (int i = 0; i < arguments.length ; i++) {
            replace[i]= arguments[i];
        }
        Map<String,Object> exchangeConfig = new HashMap<String, Object>(){
            {
                put("name","x-dead-letter-exchange");
                put("value","${spring.application.name}"+"-DLX");
                put("type","java.lang.String");
            }
        };
        Argument deadLetterExChange =(Argument) AnnotationParser.annotationForMap(Argument.class,exchangeConfig);
        Map<String,Object> routingKeyConfig = new HashMap<String, Object>(){
            {
                put("name", "x-dead-letter-routing-key");
                put("value","delayed-topic-"+"${spring.application.name}"+".default-group-"+"${spring.application.name}");
                put("type","java.lang.String");
            }
        };
        Argument deadLetterRoutingKey = (Argument) AnnotationParser.annotationForMap(Argument.class,routingKeyConfig);
        replace[replace.length-2]=deadLetterExChange;
        replace[replace.length-1]=deadLetterRoutingKey;
        return  replace;
    }

    private TypeMetadata buildMetadata(Class<?> targetClass) {
        Collection<RabbitListener> classLevelListeners = findListenerAnnotations(targetClass);
        final boolean hasClassLevelListeners = classLevelListeners.size() > 0;
        final List<ListenerMethod> methods = new ArrayList<>();
        final List<Method> multiMethods = new ArrayList<>();
        //通过反射拿到类的方法的class实例
        ReflectionUtils.doWithMethods(targetClass, method -> {
            //处理@RabbitListerner注解信息
            Collection<RabbitListener> listenerAnnotations = findListenerAnnotations(method);
            if (listenerAnnotations.size() > 0) {
                methods.add(new ListenerMethod(method,
                        listenerAnnotations.toArray(new RabbitListener[listenerAnnotations.size()])));
            }
            //处理@RabbitHandler注解信息
            if (hasClassLevelListeners) {
                RabbitHandler rabbitHandler = AnnotationUtils.findAnnotation(method, RabbitHandler.class);
                if (rabbitHandler != null) {
                    multiMethods.add(method);
                }
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
        if (methods.isEmpty() && multiMethods.isEmpty()) {
            return TypeMetadata.EMPTY;
        }
        return new TypeMetadata(
                methods.toArray(new ListenerMethod[methods.size()]),
                multiMethods.toArray(new Method[multiMethods.size()]),
                classLevelListeners.toArray(new RabbitListener[classLevelListeners.size()]));
    }

    /**
     * 寻找类上的注解
     * @param clazz
     * @return
     */
    private Collection<RabbitListener> findListenerAnnotations(Class<?> clazz) {
        Set<RabbitListener> listeners = new HashSet<>();
        RabbitListener ann = AnnotationUtils.findAnnotation(clazz, RabbitListener.class);
        if (ann != null) {
            listeners.add(ann);
        }
        //监听多个队列
        RabbitListeners anns = AnnotationUtils.findAnnotation(clazz, RabbitListeners.class);
        if (anns != null) {
            Collections.addAll(listeners, anns.value());
        }
        return listeners;
    }

    /**
     * 寻找方法上的注解
     * @param method
     * @return
     */
    private Collection<RabbitListener> findListenerAnnotations(Method method) {
        Set<RabbitListener> listeners = new HashSet<RabbitListener>();
        RabbitListener ann = AnnotationUtils.findAnnotation(method, RabbitListener.class);
        if (ann != null) {
            listeners.add(ann);
        }
        RabbitListeners anns = AnnotationUtils.findAnnotation(method, RabbitListeners.class);
        if (anns != null) {
            Collections.addAll(listeners, anns.value());
        }
        return listeners;
    }


    /**
     *  类元数据({@link RabbitListener}、{@link RabbitHandler})的持有者
     */
    private static class TypeMetadata {

        /**
         *  {@link RabbitListener} 修饰的方法.
         */
        final ListenerMethod[] listenerMethods;

        /**
         * {@link RabbitHandler} 修饰的方法.
         */
        final Method[] handlerMethods;

        /**
         * {@link RabbitListener} 修饰的类.
         */
        final RabbitListener[] classAnnotations;

        static final TypeMetadata EMPTY = new TypeMetadata();

        private TypeMetadata() {
            this.listenerMethods = new ListenerMethod[0];
            this.handlerMethods = new Method[0];
            this.classAnnotations = new RabbitListener[0];
        }

        TypeMetadata(ListenerMethod[] methods, Method[] multiMethods, RabbitListener[] classLevelListeners) { // NOSONAR
            this.listenerMethods = methods;
            this.handlerMethods = multiMethods;
            this.classAnnotations = classLevelListeners;
        }
    }
    /**
     * 被 {@link RabbitListener}修饰的方法
     */
    private static class ListenerMethod {

        final Method method;

        final RabbitListener[] annotations;

        ListenerMethod(Method method, RabbitListener[] annotations) {
            this.method = method;
            this.annotations = annotations;
        }

    }
}
