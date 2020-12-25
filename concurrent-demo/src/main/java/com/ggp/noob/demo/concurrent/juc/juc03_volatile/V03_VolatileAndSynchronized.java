package com.ggp.noob.demo.concurrent.juc.juc03_volatile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:GGP
 * @Date:2020/4/1 20:54
 * @Description:
 * 只用volatile是不可以的，因为就算其他线程看见，count++本身不是一个原子操作
 * 所以必须要用synchronized，synchronized既能保证可见性也能保证原子性
 */
public class V03_VolatileAndSynchronized {
    public static void main(String[] args) {
        Demo3 t = new Demo3();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            threads.add(new Thread(()->{
                t.m();
            }));
        }
        for (int i = 0; i <10 ; i++) {
            threads.get(i).start();
        }
        for (int i = 0; i <10 ; i++) {
            threads.forEach((o)->{
                try {
                    o.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println(t.count);
    }
}
class Demo3{
    /*volatile*/ int count=0;
  /*  synchronized*/ void m(){
        for (int i = 0; i <10000 ; i++) {
            count++;
        }
    }
}
