package com.ggp.noob.demo.basic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author:ggp
 * @Date:2020/9/28 14:43
 * @Description:
 */
@Component
@RabbitListener(queues = "message")
public class Receiver {
    @RabbitHandler
    public void processStringMessage(String message){
        System.out.println("收到消息"+message);
    }
}
