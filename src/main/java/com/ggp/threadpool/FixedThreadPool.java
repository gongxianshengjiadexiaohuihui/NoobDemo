package com.ggp.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author:GGP
 * @Date:2020/4/22 22:47
 * @Description:
 * fixedThreadPool是一个固定线程数的线程池
 *     public static ExecutorService newFixedThreadPool(int nThreads) {
 *         return new ThreadPoolExecutor(nThreads, nThreads,
 *                                       0L, TimeUnit.MILLISECONDS,
 *                                       new LinkedBlockingQueue<Runnable>());
 *     }
 * 好处是可以进行并行计算
 * 并行vs并发？concurrent vs parallel
 *
 * 并发是指任务提交，并行是指任务执行。
 * 并发是并行的子集。并行是多个cpu可以同时处理，并发是多个任务可以同时过来。
 */
public class FixedThreadPool {
    public static void main(String[] args)throws Exception {
        long start = System.currentTimeMillis();
        getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println("single thread time:" + (end - start));

        final int cpuCoreNum =4;
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);
        MyTask task1 = new MyTask(1,50000);
        MyTask task2 = new MyTask(50001,100000);
        MyTask task3 = new MyTask(100001,150000);
        MyTask task4 = new MyTask(150001,200000);

        start = System.currentTimeMillis();
        Future<List<Integer>> future1 = service.submit(task1);
        Future<List<Integer>> future2 = service.submit(task2);
        Future<List<Integer>> future3 = service.submit(task3);
        Future<List<Integer>> future4 = service.submit(task4);
        future1.get();
        future2.get();
        future3.get();
        future4.get();
        end = System.currentTimeMillis();
        System.out.println("four thread time:"+(end-start));
    }

    static class MyTask implements Callable<List<Integer>> {
        int startPos, endPos;

        public MyTask(int startPos, int endPos) {
            this.startPos = startPos;
            this.endPos = endPos;
        }

        @Override
        public List<Integer> call() throws Exception {
            return getPrime(startPos, endPos);
        }
    }

    /**
     * 判断是否是素数
     *
     * @param num
     * @return
     */
    static boolean isPrime(int num) {
        for (int i = 2; i < num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取区间之间的所有素数
     *
     * @param start
     * @param end
     * @return
     */
    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (isPrime(i)) {
                results.add(i);
            }
        }
        return results;
    }
}
