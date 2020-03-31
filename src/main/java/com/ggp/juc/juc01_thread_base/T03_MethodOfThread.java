package com.ggp.juc.juc01_thread_base;

/**
 * @Author:GGP
 * @Date:2020/3/30 23:20
 * @Description:
 */
public class T03_MethodOfThread {
    /**
     * sleep意思就是睡眠，当前线程暂停一段时间让别的程序去运行，sleep是怎么复活的？由睡眠时间而定，等睡眠到规定的时间自动复活
     * 如果为指定时间，则由notify唤醒
     */
    public static void test_sleep() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * yield,就是当前线程正在执行的时候停止下来进入等待队列，此时系统的调度算法依然有可能把此线程拿回去重新执行，但是更大可能去执行其他线程，所以yied就是我退出一下cpu，你们能不能抢到我不管
     */
    public static  void test_yield(){
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("B" + i);
                if (i % 10 == 0) {
                    Thread.yield();
                }
            }
        }).start();
    }

    /**
     * join,意思是当前线程等待join的线程执行完毕之后在执行当前线程
     */
    public static void test_join() {
        Thread t1 = new Thread(()->{
            for (int i = 0; i <100 ; i++) {
                System.out.println("A"+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(()->{
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i <100 ; i++) {
                System.out.println("B"+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
            test_join();
    }
}
