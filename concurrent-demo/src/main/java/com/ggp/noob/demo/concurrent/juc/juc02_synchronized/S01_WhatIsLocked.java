package com.ggp.noob.demo.concurrent.juc.juc02_synchronized;

/**
 * @Author:GGP
 * @Date:2020/3/31 19:48
 * @Description:
 * 不应该是锁的是谁，而是第一个访问线程自己生成钥匙和锁然后把锁放到某个对象中，而想要访问synchronized修饰的区域，需要先开保存在对象中的锁，
 * 如果是轻量级锁（偏向锁），这个对象头MarkWord中会保存第一个访问线程的id
 */
public class S01_WhatIsLocked {
    public static void main(String[] args) {
        Demo3 demo3 = new Demo3();
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                demo3.m();
            }).start();
        }
        Demo3 demo31 = new Demo3();
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                demo31.m();
            },String.valueOf(i)).start();
        }
    }
}
class Demo1{
    private int count = 10 ;
    private Object o = new Object();
    public void  m(){
        synchronized (o){
            count--;
            System.out.println(Thread.currentThread().getName()+"count="+count);
        }
    }
}
class Demo2{
    private int count =10;
    public void m(){
        synchronized (this){
            count--;
            System.out.println(Thread.currentThread().getName()+"count="+count);
        }
    }
}
class Demo3{
    private int count=10;

    /**
     * 锁的是Demo3.class这个对象
     */
    public synchronized void m(){
        count--;
        System.out.println(Thread.currentThread().getName()+"count="+count);
    }
}
