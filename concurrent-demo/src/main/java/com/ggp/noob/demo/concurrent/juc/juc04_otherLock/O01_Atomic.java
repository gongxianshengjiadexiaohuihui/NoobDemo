package com.ggp.noob.demo.concurrent.juc.juc04_otherLock;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:GGP
 * @Date:2020/4/4 10:04
 * @Description:
 */
public class O01_Atomic {
    public static void main(String[] args) {
        Demo1 t = new Demo1();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            threads.add(new Thread(()->{
                t.m();
            }));
        }
        threads.forEach((o)->{
            o.start();
        });
        threads.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);
    }
}
class Demo1{
    AtomicInteger count = new AtomicInteger(0);
    void m(){
        for (int i = 0; i <1000 ; i++) {
            /**
             * count++,内部原理还是CAS
             */
            count.incrementAndGet();
        }
    }

}
