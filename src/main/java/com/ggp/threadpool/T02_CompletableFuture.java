package com.ggp.threadpool;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/20 21:11
 * @Description:
 * 首先它是一个future，所以就会持有一个未来可能产生的结果
 * 它其实是各种任务的一个管理类，它可以在一个更高的层面上来帮助你管理一些个你想要的各种各样的任务，
 * 比如说可以对任务进行各种各样的组合，所有任务完成之后你要执行一个什么样的结果，以及任何一个任务完成之后
 * 你要执行一个什么样的结果，还有他可以提供一个链式的处理方式Lambda的一些写法，拿到结果之后进行一个什么样的处理
 */
public class T02_CompletableFuture {
    private static void delay(){
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("after %s sleep\n",time);
    }

    private static double priceOfTM(){
        delay();
        return 1.00;
    }

    private static double priceOfTB(){
        delay();
        return 2.00;
    }

    private static double priceOfJD(){
        delay();
        return 3.00;
    }
    public static void main(String[] args) {
        long start,end;

        start = System.currentTimeMillis();
        priceOfTM();
        priceOfTB();
        priceOfJD();
        end = System.currentTimeMillis();
        System.out.println("Use serial method call"+(end -start));

        start = System.currentTimeMillis();
        /**
         * 异步执行
         */
        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(()->priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(()->priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(()->priceOfJD());
        CompletableFuture.allOf(futureTM,futureTB,futureJD).join();
        end = System.currentTimeMillis();
        System.out.println("Use Async method call"+(end -start));
        /**
         * 链式写法，Apply方法里面会将上一步的结果当做参数传入apply中的方法里，里面的方法支持lambda
         *
         */
        CompletableFuture.supplyAsync(()->priceOfJD())
                .thenApply(String::valueOf)
                .thenApply(str->"price"+str)
                .thenAccept(System.out::println);
        /**
         * 上面的操作都是异步执行，所以下面写个阻塞方法，让上面都可以执行完毕。
         */
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
