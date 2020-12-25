package com.ggp.noob.demo.concurrent.threadpool;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/20 20:32
 * @Description:
 *  future 未来 用来持有线程执行的结果，因为线程需要执行一段时间才能有结果所以是未来
 *  FutureTask实现了RunnableFuture接口，既可以当任务，又可以接受结果
 */
public class T01_Future {
    public static void main(String[] args) throws Exception{
        FutureTask<Integer> task = new FutureTask<>(()->{
            TimeUnit.MILLISECONDS.sleep(10000);
            return 1000;
        });
        new Thread(task).start();
        System.out.println(task.get());
    }
}
