package com.ggp.juc.juc05_container;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/5 16:15
 * @Description:
 * 写一个固定容量的同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class C03_SynchronizedContainer {
    public static void main(String[] args) {
        Container3<String> list=new Container3<>();
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                for (int j = 0; j <5 ; j++) {
                    System.out.println(Thread.currentThread()+":"+list.get());
                }
            },"c"+i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <2 ; i++) {
            new Thread(()->{
                for (int j = 0; j <25 ; j++) {
                    list.put(Thread.currentThread().getName()+" "+j);
                }
            },"p "+i).start();
        }

    }


}

class Container3<T>{
    private LinkedList<T> list = new LinkedList<>();
    final private int MAX=10;
    private int count = 0;

    public synchronized void put(T t){
        while(list.size() == MAX){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(t);
        count++;
        /**
         * 通知消费线程进行消费
         */
        this.notifyAll();
    }
    public synchronized T get(){
        T t = null;
        while (list.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t=list.removeFirst();
        count--;
        /**
         * 通知生产者进行生产
         */
        this.notifyAll();
        return t;
    }

}