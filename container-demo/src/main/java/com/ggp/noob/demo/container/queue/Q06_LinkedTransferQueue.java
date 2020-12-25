package com.ggp.noob.demo.container.queue;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @Author:GGP
 * @Date:2020/4/19 18:04
 * @Description:
 * TransferQueue前面几个的综合，也是用于线程间通信，
 * 不同的是可以传递多个，而且取和放都是阻塞方法
 */
public class Q06_LinkedTransferQueue {
    public static void main(String[] args) throws Exception {
        LinkedTransferQueue<String> str = new LinkedTransferQueue<>();

        new Thread(()->{
            try {
                System.out.println(str.take());
                System.out.println("test");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * transfer如果没有人来取回一直阻塞在这里
         */
        str.transfer("aaa");
        str.put("aa ");

        new Thread(()->{
            try {
                System.out.println(str.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
