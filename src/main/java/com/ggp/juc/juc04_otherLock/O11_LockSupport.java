package com.ggp.juc.juc04_otherLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author:GGP
 * @Date:2020/4/4 23:34
 * @Description:
 * LockSupport是一个比较底层的工具类，用来创建锁和其他同步工具类的基本线程阻塞原语。
 * java锁和同步框架的核心AQS就是通过LockSupport来实现线程阻塞和唤醒的
 * 1、LockSupport不需要synchronized枷锁就可以实现线程的阻塞和唤醒
 * 2、LockSupport.unpark()可以先于LockSupport.park执行，并且线程不会阻塞
 * 3、如果一个线程处于等待状态，连续调用了两次park()方法，就会使该线程永远无法被唤醒
 *
 * 实现原理：
 *
 */
public class O11_LockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                System.out.println(i);
                if(i == 5){
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        //LockSupport.unpark(t);
    }
}
