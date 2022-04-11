package com.ggp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:ggp
 * @Date:2020/9/28 14:09
 * @Description:
 */
@SpringBootApplication
@EnableRabbit
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
