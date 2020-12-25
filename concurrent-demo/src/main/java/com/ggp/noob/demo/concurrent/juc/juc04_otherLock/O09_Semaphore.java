package com.ggp.noob.demo.concurrent.juc.juc04_otherLock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/4 19:52
 * @Description:
 * 限流，同时只允许规定个数的线程工作
 * 可以设定是公平锁还是非公平锁
 */
public class O09_Semaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1,true);
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("t1 running");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("t1 running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }

        }).start();
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("t2 running");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("t2 running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
