package com.ggp.noob.demo.deadletter.deal;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Created by gongguanpeng on 2022/3/7 10:27
 */
@Order(2)
@Component
public class Deal1 implements DeadLetterMessageDeal {
    @Override
    public void deal(Object message, Map headers) {
        System.out.println("Deal1");
    }
}
