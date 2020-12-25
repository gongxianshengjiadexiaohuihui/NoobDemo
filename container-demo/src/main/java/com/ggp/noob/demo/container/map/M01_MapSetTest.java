package com.ggp.noob.demo.container.map;

import com.ggp.noob.demo.container.Constant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:GGP
 * @Date:2020/4/17 21:26
 * @Description:
 * hashTable大部分方法都加了synchronized,虽然能保证多线程安全，但是单线程执行就可能比较慢
 * hashMap都没有加锁，高并发下回出现很多问题，目前测试出来的@死循环，@类型转换异常
 * concurrentHashMap 锁用的cas因为插入操作需要判断的地方比较多，所以插入会比较耗时
 * synchronizedMap 是对HashMap 声明了一个锁对象mutex,从道理来讲应该和hashTable的效率是差不多的
 */
public class M01_MapSetTest {
    static Map<UUID,UUID> m ;

    static UUID[] keys = new UUID[Constant.COUNT];
    static UUID[] values = new UUID[Constant.COUNT];

    static {
        for (int i = 0; i <keys.length ; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread{
        int start;
        int mount = Constant.COUNT/Constant.THREAD_COUNT;

        public MyThread(int start){
            this.start = start;
        }
        @Override
        public void run(){
            for (int i = start; i <start+ mount; i++) {
                m.put(keys[i],values[i]);
            }
        }
    }
    static class Test{
        public static long start(Map<UUID,UUID> map){
            m = map;
            long start = System.currentTimeMillis();
            Thread[] threads = new Thread[Constant.THREAD_COUNT];
            for (int i = 0; i <threads.length ; i++) {
                threads[i] = new MyThread(i * (Constant.COUNT/Constant.THREAD_COUNT));
            }
            for(Thread thread:threads){
                thread.start();
            }
            for(Thread thread:threads){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("hashTable-size:"+m.size());
            return (end-start);
        }
    }

    /**
     * 某次测试结果
     * hashTable-size:1000000
     * hashTable-time:589
     * hashTable-size:874500
     * hashMap-time:310
     * hashTable-size:1000000
     * concurrentHashMap-time:1161
     * hashTable-size:1000000
     * synchronizedHashMap-time:396
     */
    public static void main(String[] args) {
        long time = Test.start(new Hashtable<>());
        System.out.println("hashTable-time:"+time);
        time = Test.start(new HashMap<>());
        System.out.println("hashMap-time:"+time);
        time = Test.start(new ConcurrentHashMap<>());
        System.out.println("concurrentHashMap-time:"+time);
        time = Test.start(Collections.synchronizedMap(new HashMap<>()));
        System.out.println("synchronizedHashMap-time:"+time);
    }

}

