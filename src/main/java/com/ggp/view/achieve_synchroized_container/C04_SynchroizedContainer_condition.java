package com.ggp.view.achieve_synchroized_container;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:GGP
 * @Date:2020/4/5 20:02
 * @Description:
 */
public class C04_SynchroizedContainer_condition {
    public static void main(String[] args) {
        Container4<String> list=new Container4<>();
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

class Container4<T> {
    private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition product = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t) {
        lock.lock();
        try {
            while (list.size() == MAX) {
                try {
                    product.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(t);
            count++;
            /**
             * 通知消费线程进行消费
             */
            consumer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T t = null;
        lock.lock();
        try {
            while (list.size() == 0) {
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t = list.removeFirst();
            count--;
            /**
             * 通知生产者进行生产
             */
            product.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }
}
