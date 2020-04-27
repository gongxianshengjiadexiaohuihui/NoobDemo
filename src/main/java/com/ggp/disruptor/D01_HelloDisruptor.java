package com.ggp.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

/**
 * @Author:GGP
 * @Date:2020/4/27 21:47
 * @Description:
 */
public class D01_HelloDisruptor {
    public static void main(String[] args) {
        /**
         * 创建事件工厂
         */
        LongEventFactory factory = new LongEventFactory();
        /**
         * 环形队列大小，必须是2的n次幂值
         *  利于2进制计算
         *  比如长度是8，放入第12个元素，放入的数组下标就是12%8=12&(8-1)
         */
        int bufferSize = 1024;
        /**
         * 初始化
         */
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,bufferSize,Executors.defaultThreadFactory());
        /**
         * 配置handler
         */
        disruptor.handleEventsWith(new LongEventHandler());
        /**
         * 获取环形数组用于发布事件
         */
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();


        /**
         *  拿到可以放入事件的下标
         */
        long sequence = ringBuffer.next();
        System.out.println(sequence);
        try {
            LongEvent event = ringBuffer.get(sequence);
            event.set(8888L);
        } finally {
            /**
             * 发布一个事件（生产一个）
             */
            ringBuffer.publish(sequence);
        }

        /**
         * 等同上面
         */
        EventTranslatorOneArg<LongEvent,Long> translator =new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Long aLong) {
                longEvent.set(aLong);
            }
        };
        ringBuffer.publishEvent(translator,8888L);


    }
}
