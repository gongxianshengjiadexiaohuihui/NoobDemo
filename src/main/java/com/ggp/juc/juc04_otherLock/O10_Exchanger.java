package com.ggp.juc.juc04_otherLock;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/4 20:02
 * @Description:
 * 用于两个线程通信交换数据
 */
public class O10_Exchanger {
    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(()->{
            String s="T1";
            try {
                s =exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" "+s);
        },"t1").start();
        new Thread(()->{
            String s="T2";
            try {
                TimeUnit.SECONDS.sleep(3);
                s =exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" "+s);
        },"t2").start();
    }
}
