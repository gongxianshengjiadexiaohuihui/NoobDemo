package com.ggp;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:ggp
 * @Date:2020/9/28 20:58
 * @Description:
 */
@Configuration
public class RocketMqConfig {
    @Value("${rocket.nameSrvAddr}")
    private String nameSrvAddr;
    @Value("${rocket.push.groupName}")
    private String pushGroup;
    @Value("${rocket.pull.groupName}")
    private String pullGroup;
    @Value("${rocket.topic.eat}")
    private String topic;

    @Bean
    public DefaultMQProducer producer() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer(pushGroup);
        producer.setNamesrvAddr(nameSrvAddr);
        /**
         * 发送消息到队列前需要进行一些内部的初始化
         */
        producer.start();
        System.out.println("producer is start .........");
        return producer;
    }

    /**
     * 由系统触发，相当于监听到消息后系统去调用回调函数
     * @return
     * @throws Exception
     */
    //@Bean
    public DefaultMQPushConsumer pushConsumer() throws Exception{
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(pushGroup);
        pushConsumer.setNamesrvAddr(nameSrvAddr);
        pushConsumer.subscribe(topic,"*");
        pushConsumer.setMessageListener(new MessageResolveListener());
        /**
         * 进行内部的一些初始化
         */
        pushConsumer.start();
        System.out.println("pushConsumer is start .........");
        return pushConsumer;
    }

    /**
     * 由客户端自己去拉取消息处理
     * @return
     * @throws Exception
     */
    @Bean
    public DefaultLitePullConsumer pullConsumer() throws Exception{
        DefaultLitePullConsumer pullConsumer = new DefaultLitePullConsumer(pullGroup);
        pullConsumer.setNamesrvAddr(nameSrvAddr);
        pullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        pullConsumer.subscribe(topic,"*");
        pullConsumer.start();
        return pullConsumer;
    }
}
