package com.ggp.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * @Author:GGP
 * @Date:2020/4/27 22:20
 * @Description:
 */
public class D02_Lambda {
    public static void main(String[] args)throws Exception {
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new,bufferSize,DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith((event,sequence,endOfBatch)->{
            System.out.println("Event:"+event);
        });
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event,sequence)->{
            event.set(8888L);
        });
        System.in.read();
    }
}
