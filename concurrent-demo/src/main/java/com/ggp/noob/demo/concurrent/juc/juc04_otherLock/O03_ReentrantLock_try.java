package com.ggp.noob.demo.concurrent.juc.juc04_otherLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:GGP
 * @Date:2020/4/4 10:54
 * @Description:
 * 1、ReentrantLock是可重入锁
 */
public class O03_ReentrantLock_try {
    public static void main(String[] args) {
        Demo2 t = new Demo2();
        new Thread(t::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t::m2).start();
    }
}

/**
 * 可以进行尝试获取锁
 */
class Demo2{
    Lock lock = new ReentrantLock();
    void m1(){
        try {
            lock.lock();
            for (int i = 0; i <3 ; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    void m2(){
        boolean locked = false;
        try {
            locked= lock.tryLock(1,TimeUnit.SECONDS);
            System.out.println("m2....."+locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(locked){
                lock.unlock();
            }
        }
    }
}

