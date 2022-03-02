package com.ggp.noob.demo.delayqueue.deadletter;

import com.ggp.noob.demo.delayqueue.base.DelayMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author Created by gongguanpeng on 2022/3/2 18:02
 */
@EnableBinding(DeadLetterQueue.class)
@Component
public class DeadLetterDeal {
    @StreamListener(DeadLetterQueue.INPUT)
    public void receive(@Payload DelayMessage message){
        System.out.println("开始消费死信============================");
    }
}
