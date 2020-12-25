package com.ggp.noob.demo.container.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @Author:GGP
 * @Date:2020/4/27 21:41
 * @Description:
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public static long count =0;

    /**
     *
     * @param longEvent  处理的事件
     * @param l   ringBuffer序列号
     * @param b   是否为最后一个元素
     * @throws Exception
     */
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        count++;
        System.out.println("["+Thread.currentThread().getName()+"]"+longEvent+" 序号"+l);
    }
}
