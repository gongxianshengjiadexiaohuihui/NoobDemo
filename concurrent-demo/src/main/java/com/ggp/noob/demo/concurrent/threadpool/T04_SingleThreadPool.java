package com.ggp.noob.demo.concurrent.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:GGP
 * @Date:2020/4/22 22:19
 * @Description:
 * 一个线程创建线程池的好处
 * 1、一个线程可以保证我们扔进去的任务是顺序执行的
 * 2、线程池是有任务队列的
 * 3、线程池可以提供声明周期管理
 */
public class T04_SingleThreadPool {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i <5 ; i++) {
            final int j=i;
            service.execute(()->{
                System.out.println(j+""+Thread.currentThread().getName());
            });
        }
    }
}
