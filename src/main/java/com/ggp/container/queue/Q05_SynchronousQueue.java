package com.ggp.container.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @Author:GGP
 * @Date:2020/4/19 17:51
 * @Description:
 * 这个容器的大小为0,add方法就会直接报错，如果没有消费者等着消费（take），生产者(put)就会阻塞在哪里直到有消费者来消费。
 * 内部实现是transfer
 */
public class Q05_SynchronousQueue {
    public static void main(String[] args) throws Exception{
        BlockingQueue<String> str = new SynchronousQueue<>();

            new Thread(()->{
            try {
                System.out.println(str.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        str.put("aaa");
        System.out.println(str.size());
    }
}
