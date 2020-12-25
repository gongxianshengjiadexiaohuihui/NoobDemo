package com.ggp.noob.demo.concurrent.juc.juc04_otherLock;

import java.util.concurrent.CountDownLatch;

/**
 * @Author:GGP
 * @Date:2020/4/4 16:27
 * @Description:
 * 倒计时，倒计时结束，门栓放开
 */
public class O05_CountDownLatch {
    public static void main(String[] args) {
        /**
         * join和countLatch可以获取相同的效果，但是
         * count更灵活，可以通过条件去灵活使用countDown
         */
        usingCountDownLatch();
        usingJoin();
    }
    private static void usingCountDownLatch(){
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        for (int i = 0; i <threads.length ; i++) {
            threads[i] = new Thread(()->{
               int result = 0;
                for (int j = 0; j <result ; j++) {
                    result+=j;
                }
                latch.countDown();
            });
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end latch");
    }
    private static void usingJoin(){
        Thread[] threads = new Thread[100];
        for (int i = 0; i <threads.length ; i++) {
            threads[i] = new Thread(()->{
                int result = 0;
                for (int j = 0; j < 10000; j++) {
                    result+=j;
                }
            });
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end join");
    }
}
