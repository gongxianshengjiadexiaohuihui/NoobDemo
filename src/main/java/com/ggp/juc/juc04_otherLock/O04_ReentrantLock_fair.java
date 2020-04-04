package com.ggp.juc.juc04_otherLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:GGP
 * @Date:2020/4/4 16:16
 * @Description:
 * reentrant可以设定是公平锁还是非公平锁
 * 公平锁属于先到先得，后到的在队列里等待
 * 非公平锁是谁先抢到谁得，跟谁先来没关系
 */
public class O04_ReentrantLock_fair {
    public static void main(String[] args) {
        Demo3 t = new Demo3();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();
    }
}
class Demo3 extends Thread{
    private static ReentrantLock lock = new ReentrantLock(true);
    @Override
    public void run(){
        for (int i = 0; i < 100 ; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"获得锁");
            } finally {
                lock.unlock();
            }
        }
    }
}
