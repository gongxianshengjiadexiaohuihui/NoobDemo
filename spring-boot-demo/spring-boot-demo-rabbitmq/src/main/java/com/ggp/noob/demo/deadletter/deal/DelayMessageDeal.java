package com.ggp.noob.demo.deadletter.deal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ggp.noob.demo.delayqueue.base.DelayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * @Author Created by gongguanpeng on 2022/3/7 10:24
 */
@Component
public class DelayMessageDeal implements DeadLetterMessageDeal{
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        //序列化LocalDateTime问题
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void deal(Object message, Map headers) {
        DelayMessage delayMessage;
        try {
            delayMessage = objectMapper.readValue(message.toString(), DelayMessage.class);
        } catch (IOException e) {
            return;
        }
        if(null == delayMessage){
            return;
        }
        System.out.println(delayMessage.getBody());
        System.out.println(headers);
    }
}
