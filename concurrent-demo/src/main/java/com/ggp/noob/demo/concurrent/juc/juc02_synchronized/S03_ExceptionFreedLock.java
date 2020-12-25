package com.ggp.noob.demo.concurrent.juc.juc02_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/3/31 20:35
 * @Description:
 * 当执行过程中抛出异常，锁就会被释放
 */
public class S03_ExceptionFreedLock {
    public static void main(String[] args) {
        Demo5 t = new Demo5();
        new Thread(()->{
            t.m();
        },"t1").start();
        new Thread(()->{
            t.m();
        },"t2").start();
    }
}
class Demo5{
    int count =0;
    synchronized void m(){
        System.out.println(Thread.currentThread().getName()+"start");
        while (true){
            count++;
            System.out.println(Thread.currentThread().getName()+"count="+count);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count==5){
                /**
                 * 抛出异常
                 */
                int i = 1/0;
            }
        }
    }
}
