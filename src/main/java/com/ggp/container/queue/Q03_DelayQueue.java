package com.ggp.container.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/18 23:00
 * @Description:
 * DelayQueue可以实现时间排序，里面放的元素必须实现Delayed接口
 * 这个接口有俩个方法
 * 1、获取等待的时间
 * 2、compareTo接口定义比较的方法
 * 这个队列是放的时候就已经按时间排好序了，本质是用了一个PriorityQueue,内部是一个二叉树，类似于堆排序的方式进行排序，最小的在堆的上面
 */
public class Q03_DelayQueue {
    public static BlockingQueue<MyTask> tasks = new DelayQueue<>();

    public static void main(String[] args)throws Exception {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask("t1", now + 1000);
        MyTask t2 = new MyTask("t2", now + 200);
        MyTask t3 = new MyTask("t3", now + 1500);
        MyTask t4 = new MyTask("t4", now + 2500);
        MyTask t5 = new MyTask("t5", now + 500);
        tasks.put(t1);
        tasks.put(t2);
        tasks.put(t3);
        tasks.put(t4);
        tasks.put(t5);
        System.out.println(tasks);
        for (int i = 0; i <5 ; i++) {
            System.out.println(tasks.take());
        }
    }

    static class MyTask implements Delayed {
        String name;
        long runningTime;

        public MyTask(String name, long runningTime) {
            this.name = name;
            this.runningTime = runningTime;
        }

        /**
         * 返回延迟时间的剩余时间
         *
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            /**
             * 第一个参数是转换的元数据，第二个参数是要转化的格式（秒，毫秒...）
             */
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 比较判断谁先谁后
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return "MyTask{" +
                    "name='" + name + '\'' +
                    ", runningTime=" + runningTime +
                    '}';
        }
    }
}
