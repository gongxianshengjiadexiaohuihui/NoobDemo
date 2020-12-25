package com.ggp.noob.demo.concurrent.juc.juc04_otherLock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:GGP
 * @Date:2020/4/4 19:35
 * @Description:
 * 读锁是共享锁，来了其他线程只允许读，不允许写
 * 写锁是排他锁，来了其他线程不允许读，也不允许写，必须等写完，其他线程才能操作
 */
public class O08_ReadWriteLock {
    static Lock lock = new ReentrantLock();
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock) {
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("read over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock,int v){
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            value = v;
            System.out.println("write over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Runnable read = ()->read(readLock);
        Runnable write = ()->write(writeLock,new Random().nextInt());
//        Runnable read = ()->read(lock);
//        Runnable write = ()->write(lock,new Random().nextInt());

        for (int i = 0; i <18 ; i++) {
            new Thread(read).start();
        }
        for (int i = 0; i <2 ; i++) {
            new Thread(write).start();
        }
    }
}

