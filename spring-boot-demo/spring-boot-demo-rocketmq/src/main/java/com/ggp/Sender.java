package com.ggp;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author:ggp
 * @Date:2020/9/28 21:38
 * @Description:
 */
@Component
public class Sender {
    @Autowired
    private DefaultMQProducer producer;
    @Value("${rocket.topic.eat}")
    private String topic;

    public Object send(String msg) throws Exception{
        Message message = new Message(topic,msg.getBytes());
        return producer.send(message);
    }
}
