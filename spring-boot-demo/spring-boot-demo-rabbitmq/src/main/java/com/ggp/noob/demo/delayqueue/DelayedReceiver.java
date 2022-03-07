package com.ggp.noob.demo.delayqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggp.noob.demo.delayqueue.base.CallBackFunction;
import com.ggp.noob.demo.delayqueue.base.DelayMessage;
import com.ggp.noob.demo.delayqueue.config.DelayedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;

/**
 * @Author Created by gongguanpeng on 2022/2/22 16:51
 */
@EnableBinding(DelayedQueue.class)
@Component
public class DelayedReceiver {
    private Logger logger = LoggerFactory.getLogger(DelayedReceiver.class);
    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener(DelayedQueue.INPUT)
    public void receive(@Payload DelayMessage message) throws Exception {
        message.getMessageAttribute().setReceiveTime(LocalDateTime.now());
        if(logger.isDebugEnabled()){
            logger.debug(message.printAttributeInfo());
        }
        //反序列化获取lambda方法
        CallBackFunction function = resolveLambdaFunction(message);
        //请求参数类型进行反序列化
        Object param = parseMessageBody(message);
        //执行方法
        function.accept(param);
        throw new RuntimeException("test");

    }

    private CallBackFunction resolveLambdaFunction(DelayMessage message) throws IOException, ClassNotFoundException {
        ObjectInput oi = new ObjectInputStream(new ByteArrayInputStream(message.getMessageAttribute().getCallBackMethodInfo().getCallback()));
        CallBackFunction function = (CallBackFunction) oi.readObject();
        return function;
    }

    private Object parseMessageBody(DelayMessage message) throws Exception {
        String paramClass = message.getMessageAttribute().getCallBackMethodInfo().getParamClassName();
        Object body = message.getBody();
        if(null == paramClass){
            return body;
        }
        Class type = Class.forName(paramClass);
        try {
            String value = objectMapper.writeValueAsString(body);
            return objectMapper.readValue(value,type);
        } catch (Exception e) {
            throw new SerializationFailedException("Deserialization class "+paramClass+" fail");
        }
    }

}

