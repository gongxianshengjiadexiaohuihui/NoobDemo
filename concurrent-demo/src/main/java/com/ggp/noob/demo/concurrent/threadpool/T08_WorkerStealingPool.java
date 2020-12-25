package com.ggp.noob.demo.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/26 23:05
 * @Description:
 *     public static ExecutorService newWorkStealingPool() {
 *         return new ForkJoinPool
 *             (Runtime.getRuntime().availableProcessors(),
 *              ForkJoinPool.defaultForkJoinWorkerThreadFactory,
 *              null, true);
 *     }
 *  每一个线程池都有自己的任务队列，如果自己的任务执行完了可以从别的线程的任务队列中获取任务执行
 *  本质是一个forkJoinPool
 */
public class T08_WorkerStealingPool {
    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newWorkStealingPool();
        /**
         * 获取处理器数量，包括虚拟的资源
         */
        System.out.println(Runtime.getRuntime().availableProcessors());
        service.execute(new Demo(1000));
        service.execute(new Demo(2000));
        service.execute(new Demo(2000));
        service.execute(new Demo(2000));
        service.execute(new Demo(2000));
        /**
         * 由于产生的是精灵线程（守护线程，后台线程），主线程不阻塞的话，看不到输出
         */
        System.in.read();
    }
    static class Demo implements Runnable{
        int time;

        public Demo(int time) {
            this.time = time;
        }

        @Override
        public void run( ){
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time+" "+Thread.currentThread().getName());
        }
    }
}
