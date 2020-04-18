package com.ggp.container.queue;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/18 22:23
 * @Description:
 * 内部使用的是ReentrantLock 可以被打断 而且可以用condition 分别通知生产者和消费者进行生产或者消费
 */
public class Q02_LinkedBlockingQueue {
    public static BlockingDeque<String> str = new LinkedBlockingDeque<>();
    public static Random random = new Random();

    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i <100 ; i++) {
                try {
                    /**
                     * 如果是满了也会阻塞
                     */
                    str.put("a"+i);
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"p1").start();
        for (int i = 0; i <5 ; i++) {
            new Thread(()->{
                for(;;){
                    try {
                        /**
                         * 如果空了就会线程就会阻塞
                         */
                        System.out.println(Thread.currentThread().getName()+"take-"+str.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"c"+i).start();
        }


    }
}
