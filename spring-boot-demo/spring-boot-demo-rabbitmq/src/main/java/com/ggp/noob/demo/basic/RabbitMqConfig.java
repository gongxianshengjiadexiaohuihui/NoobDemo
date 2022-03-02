package com.ggp.noob.demo.basic;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:ggp
 * @Date:2020/9/28 14:24
 * @Description:
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public Queue messageQueue(){
        return new Queue("message");
    }
}
