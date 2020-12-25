package com.ggp.noob.demo.concurrent.view.alternate_print;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:GGP
 * @Date:2020/4/19 19:15
 * @Description:
 * 分出了两个condition，同时让两个线程竞争一个锁，执行完后释放锁并通知另外一个线程去拿锁
 * lock锁的这段代码是执行前都会校验是否拥有锁，包括执行完一段回来后依然要校验
 */
public class A03_ReentrantLock {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition numLock = lock.newCondition();
        Condition letterLock = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < Constant.num.length; i++) {
                    System.out.println(Constant.num[i]);
                    letterLock.signal();
                    numLock.await();

                }
               letterLock.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i <Constant.letter.length ; i++) {
                    System.out.println(Constant.letter[i]);
                    numLock.signal();
                    letterLock.await();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();
    }

}
