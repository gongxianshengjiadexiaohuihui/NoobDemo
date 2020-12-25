package com.ggp.noob.demo.concurrent.juc.juc01_thread_base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @Author:GGP
 * @Date:2020/3/30 20:12
 * @Description:
 */
public class T02_HowCreateThreadAndStart {
    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("hello MyThread");
        }
    }
    static class MyRun  implements Runnable{
        @Override
        public void run() {
            System.out.println("hello MyRun");
        }
    }
    static class MyCall  implements Callable<String>{
        @Override
        public String call() throws Exception {
            System.out.println("hello MyCall");
            return "success";
        }
    }

    public static void main(String[] args) {
        /**
         * 启动线程的五个方法
         */
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(()->{
            System.out.println("hello Lambda");
        }).start();
        Thread t = new Thread(new FutureTask<String>(new MyCall()));
        t.start();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            System.out.println("hello executor");
        });
        service.shutdown();
    }
}
