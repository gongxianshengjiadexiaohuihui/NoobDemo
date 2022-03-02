package com.ggp.noob.demo.delayqueue.deadletter;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author Created by gongguanpeng on 2022/3/2 16:16
 */
public interface DeadLetterQueue {
    String INPUT = "dead-letter-topic-input";

    @Input(INPUT)
    SubscribableChannel input();
}
