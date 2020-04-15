package com.ggp.juc.juc07_threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/15 23:07
 * @Description:
 * 观察源码可以发现
 * p.set的时候做了以下几步
 * 1、拿到当前线程
 * 2、获取当前线程的ThreadLocals(这是一个map对象)key是TheadLocal也就是p,value是Person;
 * 3、这里的key继承了弱引用？为什么是弱引用呢，因为p是一个局部变量，很快就会被回收，这时候p对象还是会被key一直指向，如果key是强引用，而且线程是在线程池里放着，就一直回收不了。造成内存溢出所以，这里用了弱引用。
 * 4、key被回收后，在调用get（）方法访问的时候会用getEntryAfterMiss（）这个方法清除这个节点（回收value）因为整个entry是被强引用指着
 */
public class T02_Hello_TheadLocal {
    static ThreadLocal<Person> p = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(p.get());
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.set(new Person());
        }).start();
    }
}
