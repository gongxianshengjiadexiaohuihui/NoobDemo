package com.ggp.noob.demo.container.collection;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/17 23:09
 * @Description:
 *  vector也是大部分方法都加了synchronized,保证线程安全
 *  虽然size和remove方法都是原子操作，但是在两个操作之间还是可能会被其他线程插入造成线程不安全因此需要加锁
 */
public class C02_VectorGetTest {
    static Vector<String> tickets = new Vector<>();
    static {
        for (int i = 0; i < 15; i++) {
            tickets.add("票编号"+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <3 ; i++) {
            new Thread(()->{
                while(tickets.size()>0){
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"销售了"+ tickets.remove(0));
                }
            }).start();
        }
    }
}
