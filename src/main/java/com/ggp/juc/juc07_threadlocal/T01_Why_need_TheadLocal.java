package com.ggp.juc.juc07_threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/15 23:01
 * @Description:
 * 有时候我们可能想要线程独有一个“p”
 */
public class T01_Why_need_TheadLocal {
    volatile static Person p = new Person();
    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(p.name);
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name = "test";
        }).start();
    }
}
class Person{
    String name ="ggp";
}
