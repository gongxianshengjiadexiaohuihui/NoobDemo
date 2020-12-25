package com.ggp.noob.demo.container.disruptor;


import com.lmax.disruptor.EventFactory;

/**
 * @Author:GGP
 * @Date:2020/4/27 21:06
 * @Description:
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent() ;
    }
}
