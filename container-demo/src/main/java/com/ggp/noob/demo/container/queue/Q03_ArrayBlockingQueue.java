package com.ggp.noob.demo.container.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/18 22:45
 * @Description:
 * 对比@see Q02_LinkedBlockingQueue 是有界的，可以设定大小，put的时候满的时候也会阻塞
 *
 */
public class Q03_ArrayBlockingQueue {
    public static BlockingQueue<String> str = new ArrayBlockingQueue<>(10);
    public static void main(String[] args) throws Exception {
        for (int i = 0; i <10 ; i++) {
            try {
                str.put("a"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * 可以设定超时时间，时间到了就不在尝试
         */
        str.offer("aaa",3,TimeUnit.SECONDS);
        System.out.println(str);
    }
}
