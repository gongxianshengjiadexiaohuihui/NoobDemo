package com.ggp.noob.demo.delayqueue.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author Created by gongguanpeng on 2022/2/22 16:36
 */
public interface DelayedQueue {
    String OUTPUT = "delayed-topic-output";
    String INPUT = "delayed-topic-input";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();
}
