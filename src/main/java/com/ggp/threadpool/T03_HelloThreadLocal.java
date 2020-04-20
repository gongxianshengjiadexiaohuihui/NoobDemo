package com.ggp.threadpool;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/20 21:43
 * @Description:
 */
public class T03_HelloThreadLocal {
    static class Task implements Runnable{
        private int i;

        public Task(int i){
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"Task"+i);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "i=" + i +
                    '}';
        }
    }
    public static void main(String[] args) {
        /**
         * 线程池的7个参数
         * 1、corePoolSize 核心线程数，最开始的时候，这个线程池是有一定核心线程数的
         * 2、maximumPoolSize 最大线程数，线程数不够了，最大可以扩展到的线程数
         * 3、keepAliveTime生存时间，意思是这个线程有很长时间不干活了，归还给操作系统
         * 4、TimeUnit  上一个参数的单位，时分秒
         * 5、BlockingQueue 任务队列
         * 6、ThreadFactory 线程工厂，可以指定线程的名字，线程的优先级，是否是守护线程等等
         * 7、RejectedExecutionHandler 拒绝策略，指任务队列满了之后对后续添加任务的处理，jdk支持4种，可以自定义
         *          1、Abort 抛异常
         *          2、Discard 扔掉，不抛异常
         *          3、DiscardOldest 扔掉排队时间最久的
         *          4、CallerRuns 调用者处理任务
         */
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
          2,
          4,
          60,
          TimeUnit.SECONDS,
          new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        /**
         * 任务队列的长度是4，说明8个任务会被四个线程拿走四个然后阻塞，剩下四个在任务队列里，再来一个任务的话就根据策略由调用者执行也就是main线程
         */
        for (int i = 0; i <8 ; i++) {
            poolExecutor.execute(new Task(i));
        }
        System.out.println(poolExecutor.getQueue());
        poolExecutor.execute(new Task(100));
        System.out.println(poolExecutor.getQueue());
        poolExecutor.shutdown();
    }
}
