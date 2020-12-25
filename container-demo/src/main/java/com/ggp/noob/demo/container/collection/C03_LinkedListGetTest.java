package com.ggp.noob.demo.container.collection;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/17 23:14
 * @Description:
 * 加了锁保证售票这个动作是原子操作
 */
public class C03_LinkedListGetTest {
    static final Object mutex = new Object();
    static List<String> tickets = new LinkedList<>();
    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号"+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                while(true) {
                    synchronized (mutex) {
                        if(tickets.size()<=0){
                            break;
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "销售了" + tickets.remove(0));
                    }
                }
            }).start();
        }
    }
}
