package com.ggp.noob.demo.container.disruptor;

/**
 * @Author:GGP
 * @Date:2020/4/27 21:05
 * @Description:
 */
public class LongEvent {
    private long value;

    public void set(long value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
