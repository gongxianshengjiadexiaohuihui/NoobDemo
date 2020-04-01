package com.ggp.juc.juc02_synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/1 22:35
 * @Description:
 */
public class S04_FineCoarseLock {
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        Demo6 t  = new Demo6();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            threads.add(new Thread(()->{
               //t.m1();
                t.m2();
            }));
        }
        for (int i = 0; i <10 ; i++) {
            threads.get(i).start();
        }
        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(System.currentTimeMillis()-begin);
    }
}
class Demo6{
    int count = 0;
    synchronized void m1(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     void m2(){
         try {
             TimeUnit.SECONDS.sleep(2);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         synchronized (this) {
             count++;
         }

         try {
             TimeUnit.SECONDS.sleep(2);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    }
}

