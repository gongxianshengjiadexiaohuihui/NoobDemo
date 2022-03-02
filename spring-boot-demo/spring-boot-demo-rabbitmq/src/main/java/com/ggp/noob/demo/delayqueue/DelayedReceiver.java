package com.ggp.noob.demo.delayqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggp.noob.demo.delayqueue.config.DelayedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Author Created by gongguanpeng on 2022/2/22 16:51
 */
@EnableBinding(DelayedQueue.class)
@Component
public class DelayedReceiver {
    private Logger logger = LoggerFactory.getLogger(DelayedReceiver.class);

    @StreamListener(DelayedQueue.INPUT)
    public void receive(@Payload DelayMessage message) throws Exception {
        message.getMessageAttribute().setReceiveTime(LocalDateTime.now());
        if(logger.isDebugEnabled()){
            logger.debug(message.printAttributeInfo());
        }
        //反序列化获取lambda方法
        CallBackFunction function = resolveLambdaFunction(message);
        //解析lambda方法的请求参数类型
        Class paramClass = resolveRequestParam(function);
        //请求参数类型进行反序列化
        Object param = parseMessageBody(paramClass,message.getBody());
        //执行方法
        function.accept(param);

    }

    private CallBackFunction resolveLambdaFunction(DelayMessage message) throws IOException, ClassNotFoundException {
        ObjectInput oi = new ObjectInputStream(new ByteArrayInputStream(message.getMessageAttribute().getCallback()));
        CallBackFunction function = (CallBackFunction) oi.readObject();
        return function;
    }

    private Object parseMessageBody(Class paramClass, Object body) throws SerialException {
        if(null == paramClass){
            return body;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(body);
            return mapper.readValue(value,paramClass);
        } catch (Exception e) {
            throw new SerializationFailedException("Deserialization class "+paramClass.getName()+" fail");
        }
    }

    private Class resolveRequestParam(CallBackFunction function) throws Exception {
        Method method = function.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda =(SerializedLambda) method.invoke(function);
        String signature = serializedLambda.getImplMethodSignature();
        String type = signature.substring(1, signature.indexOf(")"));
        if(type.charAt(0)=='L'){
            String className = type.substring(1,type.length()-1).replace("/",".");
            return Class.forName(className);
        }
        return null;
    }
}

