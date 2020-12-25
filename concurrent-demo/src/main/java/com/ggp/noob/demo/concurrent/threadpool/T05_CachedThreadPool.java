package com.ggp.noob.demo.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/22 22:36
 * @Description:
 *     public static ExecutorService newCachedThreadPool() {
 *         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
 *                                       60L, TimeUnit.SECONDS,
 *                                       new SynchronousQueue<Runnable>());
 *     }
 *     这个线程是没有核心线程数的，它的任务队列是SynchronousQueue,也就是来一个任务，如果线程池有线程有用，如果没有就启动一个线程
 *     如果过了60秒，还有线程空闲就回收线程
 */
public class T05_CachedThreadPool {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println(service);
        for (int i = 0; i <2 ; i++) {
            service.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(service);
        }
        TimeUnit.SECONDS.sleep(61);
        System.out.println(service);
    }
}
