package com.ggp.container.map;

import com.ggp.container.Constant;

import java.util.Hashtable;
import java.util.UUID;

/**
 * @Author:GGP
 * @Date:2020/4/17 21:26
 * @Description:
 * hashTable大部分方法都加了synchronized,虽然能保证多线程安全，但是单线程执行就可能比较慢
 */
public class M01_HashTableTest {
    static Hashtable<UUID,UUID> m = new Hashtable<>();

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

    public static void main(String[] args) {
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
        System.out.println("hashTable-time:"+(end - start));
        System.out.println("hashTable-size:"+m.size());
    }

}

