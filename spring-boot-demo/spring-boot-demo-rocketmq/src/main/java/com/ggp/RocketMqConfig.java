package com.ggp;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:ggp
 * @Date:2020/9/28 20:58
 * @Description:
 */
@Configuration
public class RocketMqConfig {
    @Value("${rocket.nameSrvAddr}")
    private String nameSrvAddr;
    @Value("${rocket.consumer.groupName}")
    private String consumerGroup;
    @Value("${rocket.producer.groupName}")
    private String producerGroup;
    @Value("${rocket.topic.eat}")
    private String topic;

    @Bean
    public DefaultMQProducer producer() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameSrvAddr);
        /**
         * 发送消息到队列前需要进行一些内部的初始化
         */
        producer.start();
        System.out.println("producer is start .........");
        return producer;
    }
    @Bean(name="pushConsumer")
    public DefaultMQPushConsumer pushConsumer() throws Exception{
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(consumerGroup);
        pushConsumer.setNamesrvAddr(nameSrvAddr);
        Map<String,String> subscription = new HashMap<>();
        subscription.put(topic,"*");
        pushConsumer.setSubscription(subscription);
        pushConsumer.setMessageListener(new MessageResolveListener());
        /**
         * 进行内部的一些初始化
         */
        pushConsumer.start();
        System.out.println("pushConsumer is start .........");
        return pushConsumer;
    }
}
