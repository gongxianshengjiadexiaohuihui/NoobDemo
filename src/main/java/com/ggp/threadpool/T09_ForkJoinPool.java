package com.ggp.threadpool;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author:GGP
 * @Date:2020/4/26 23:20
 * @Description:
 * 利用的是递归将任务切分，然后放到队列里让线程去执行
 */
public class T09_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM=50000;
    static Random random = new Random();
    static {
        for (int i = 0; i < nums.length ; i++) {
            nums[i] = random.nextInt(100);
        }
        long begin = System.currentTimeMillis();
        System.out.println(Arrays.stream(nums).sum());
        long end = System.currentTimeMillis();
        System.out.println("single thread  time:"+(end-begin));
    }
    static class AddTask extends RecursiveTask<Long> {
        int start,end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
           if(end-start<=MAX_NUM){
               long sum = 0L;
               for (int i = start; i <end ; i++) {
                   sum+=nums[i];
               }
               return sum;
           } else{
               int middle= start+(end-start)/2;
               AddTask sub1 = new AddTask(start,middle);
               AddTask sub2 = new AddTask(middle,end);
               sub1.fork();
               sub2.fork();
               return sub1.join()+sub2.join();
           }
        }
    }

    public static void main(String[] args)throws Exception {
        long begin = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        AddTask task = new AddTask(0,nums.length);
        pool.execute(task);
        long result = task.join();
        System.out.println(result);
        long end = System.currentTimeMillis();
        System.out.println("single thread  time:"+(end-begin));
        //System.in.read();
    }
}
