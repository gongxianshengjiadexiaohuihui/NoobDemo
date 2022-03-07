package com.ggp.noob.demo.deadletter;

import com.ggp.noob.demo.deadletter.deal.DeadLetterMessageDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author Created by gongguanpeng on 2022/3/2 18:02
 */
@EnableBinding(DeadLetterQueue.class)
@Component
public class DeadLetterDeal {
    @Autowired
    private List<DeadLetterMessageDeal> works;

    @StreamListener(DeadLetterQueue.INPUT)
    public void receive(@Payload Object message, @Headers Map headers){
        System.out.println("开始消费死信============================");
        AnnotationAwareOrderComparator.sort(works);
        for(DeadLetterMessageDeal work:works){
            work.deal(message, headers);
        }
    }
}
