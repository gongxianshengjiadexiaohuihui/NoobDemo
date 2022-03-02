package com.ggp.noob.demo.basic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:ggp
 * @Date:2020/9/28 14:26
 * @Description:
 */
@Component
public class Sender {
    @Autowired
    private RabbitTemplate template;

    public void send(String message){
        template.convertAndSend("message",message);
    }
}
