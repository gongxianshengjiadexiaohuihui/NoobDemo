package com.ggp.noob.demo.delayqueue;

import com.ggp.noob.demo.delayqueue.config.DelayedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author Created by gongguanpeng on 2022/2/22 16:47
 */
@Component
public class DelayedSender {
    private Logger logger = LoggerFactory.getLogger(DelayedSender.class);

    @Resource
    private DelayedQueue delayedQueue;

    public <T> void delayedMessage(TimeUnit timeUnit, long time, T message, CallBackFunction<T> callBack) throws Exception {
        long delayTime = timeUnit.toMillis(time);
        //构建延迟消息
        DelayMessage delayMessage = new DelayMessage();
        delayMessage.setBody(message);
        //填充发送者的消息属性
        fillSenderInfo(delayMessage,delayTime);
        //序列化lambda函数
        resolveCallBackMethod(delayMessage, callBack);
        delayedQueue.output().send(MessageBuilder.withPayload(delayMessage).setHeader("x-delay",delayTime).build());
    }

    private void fillSenderInfo(DelayMessage message,long delayTime) {
        MessageAttribute messageAttribute = new MessageAttribute();
        messageAttribute.setSendTime(LocalDateTime.now());
        messageAttribute.setDelayTime(delayTime);
        message.setMessageAttribute(messageAttribute);
    }

    private void resolveCallBackMethod(DelayMessage message, CallBackFunction callBack) throws SerialException, IOException {
        //校验lambda是否实现序列化接口
        try {
            callBack.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            logger.error("what class callBack method in need implement Serializable interface !");
            throw new SerialException();
        }
        //序列化填充callback方法 jackson未支持，需要手动序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(callBack);
        message.getMessageAttribute().setCallback(bos.toByteArray());
    }
}