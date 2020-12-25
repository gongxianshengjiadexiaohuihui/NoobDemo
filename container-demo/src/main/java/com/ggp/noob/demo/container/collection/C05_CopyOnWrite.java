package com.ggp.noob.demo.container.collection;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:GGP
 * @Date:2020/4/18 21:56
 * @Description:
 * 这个集合会在写入的时候复制一份原来的集合然后往里写，写成功后再让指针指向最新的数组，这样读的时候就不用加锁了
 * 适合读非常多写入非常少的场景
 */
public class C05_CopyOnWrite {
    public static void main(String[] args) {
        List<String> lists = new CopyOnWriteArrayList<>();
        Random random = new Random();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length ; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j <1000 ; j++) {
                        lists.add("a"+random.nextInt(1000));
                    }
                }
            };
            threads[i] = new Thread(task);
        }
        runAndComputeTime(threads);
    }

    private static void runAndComputeTime(Thread[] threads) {
        long begin = System.currentTimeMillis();
        Arrays.asList(threads).forEach(t->t.start());
        Arrays.asList(threads).forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("消耗时间："+(end - begin));
    }
}
