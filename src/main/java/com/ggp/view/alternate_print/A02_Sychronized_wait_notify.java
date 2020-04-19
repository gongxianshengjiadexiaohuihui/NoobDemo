package com.ggp.view.alternate_print;

/**
 * @Author:GGP
 * @Date:2020/4/19 19:01
 * @Description:
 * 利用synchronized同步的概念每次只让一个线程拿到锁，执行完后唤醒另一个线程并让出锁，
 * 想要保证谁先运行，可以用countDownLatch或者用一个volatile 的boolean变量去标识另一个线程是否运行
 */
public class A02_Sychronized_wait_notify {
    public static void main(String[] args) {
        final Object mutex = new Object();

        new Thread(()->{
            synchronized (mutex) {
                for (int i = 0; i <Constant.num.length ; i++) {
                    try {
                        System.out.println(Constant.num[i]);
                        mutex.notify();
                        /**
                         * wait让出锁，notify并没有让出锁
                         */
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
               mutex.notify();
            }
        }).start();

        new Thread(()->{
            synchronized (mutex) {
                for (int i = 0; i <Constant.letter.length ; i++) {
                    try {
                        System.out.println(Constant.letter[i]);
                        mutex.notify();
                        /**
                         * 让出锁
                         */
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
