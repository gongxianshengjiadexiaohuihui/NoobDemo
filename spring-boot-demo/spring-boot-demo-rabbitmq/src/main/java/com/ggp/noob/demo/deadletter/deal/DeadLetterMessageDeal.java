package com.ggp.noob.demo.deadletter.deal;

import java.util.Map;

/**
 * @Author Created by gongguanpeng on 2022/3/7 10:23
 */
public interface DeadLetterMessageDeal {
    void deal(Object message, Map headers);
}
