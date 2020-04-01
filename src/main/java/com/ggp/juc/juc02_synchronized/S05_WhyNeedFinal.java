package com.ggp.juc.juc02_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/1 22:56
 * @Description:
 * 当持有锁的对象别修改，锁就会释放。
 */
public class S05_WhyNeedFinal {

    public static void main(String[] args) {
        Demo7 t = new Demo7();
        new  Thread(t::m,"t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(t::m,"t2");
      //  t.o = new Object();
        t2.start();
    }
}
class Demo7{
  /*final*/   Object  o = new Object();
    void m(){
        synchronized (o){
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
}