package com.ggp.noob.demo.concurrent.view.achieve_synchroized_container;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:GGP
 * @Date:2020/4/5 15:27
 * @Description:
 */
public class C02_ListenserContainer_lock_wait_notify {
    public static void main(String[] args) {
        final Object lock = new Object();
        Container2 list = new Container2();
        new Thread(()->{
            synchronized (lock) {
                for (int i = 1; i <11 ; i++) {
                    list.add(i);
                    if(i == 5){
                        try {
                            /**
                             * 释放锁
                             */
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        /**
                         * 通知线程2，让线程2结束
                         */
                        lock.notify();
                    }

                }
            }
        }).start();
        new Thread(()->{
            synchronized (lock){
                if(list.size() == 5){
                    System.out.println("数量已经达到5");
                    try {
                        /**
                         * 释放锁
                         */
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /**
                 * 通知线程1继续
                 */
                lock.notify();
                System.out.println("t2结束");
            }
        }).start();
    }
}
class Container2{
    private List list = new ArrayList();
    private  int count = 0;
    public void add(Object o){
        list.add(o);
        System.out.println("add"+o);
    }
    public int size(){
        return count;
    }
}