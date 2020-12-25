package com.ggp.noob.demo.concurrent.threadpool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/23 21:48
 * @Description:
 *     public ScheduledThreadPoolExecutor(int corePoolSize) {
 *         super(corePoolSize, Integer.MAX_VALUE,
 *               DEFAULT_KEEPALIVE_MILLIS, MILLISECONDS,
 *               new DelayedWorkQueue());
 *     }
 *  重点还是任务队列
 */
public class T07_ScheduledPool {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        /**
         * 周期性的执行一个任务
         */
        service.scheduleAtFixedRate(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        },0,500,TimeUnit.MILLISECONDS);
    }
}
