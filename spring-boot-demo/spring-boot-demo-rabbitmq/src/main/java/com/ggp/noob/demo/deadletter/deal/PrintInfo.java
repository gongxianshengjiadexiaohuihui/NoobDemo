package com.ggp.noob.demo.deadletter.deal;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Created by gongguanpeng on 2022/3/7 10:27
 */
@Order(1)
@Component
public class PrintInfo implements DeadLetterMessageDeal {
    @Override
    public void deal(Object message, Map headers) {
        System.out.println("打印死信信息");
        headers.keySet().forEach(
                e->{
                    System.out.println(e+":"+headers.get(e));
                }
        );
        System.out.println("message:"+message.toString());
    }
}
