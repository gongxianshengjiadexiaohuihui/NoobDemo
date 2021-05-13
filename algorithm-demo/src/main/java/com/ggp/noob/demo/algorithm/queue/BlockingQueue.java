package com.ggp.noob.demo.algorithm.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:ggp
 * @Date:2021/5/13 13:55
 * @Description: 阻塞队列
 */
public class BlockingQueue {
    private String[] queue;
    private int size;
    private int head;
    private int tail;
    //队列元素数量
    private int count;
    //非满等待队列
    private Condition notFull;
    //非空等待队列
    private Condition notEmpty;
    private ReentrantLock lock;

    public BlockingQueue(int size) {
        this.size = size;
        queue = new String[size];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public void enqueue(String item) throws InterruptedException {
        final ReentrantLock lock = this.lock;
        //获取锁，可以被中断(从等待队列退出)
        try {
            lock.lockInterruptibly();
            //防止多线程假唤醒~醒来后再做一次判断
            while (count == size) {
                //队满，放入非满情况的等待队列
                notFull.await();
            }
            queue[tail++] = item;
            if (tail == size) {
                //到数组末尾，从头开始
                tail = 0;
            }
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String dequeue() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        try {
            lock.lockInterruptibly();
            //防止多线程假唤醒~醒来后再做一次判断
            while (count == 0) {
                //队空，放入非空情况等待队列
                notEmpty.await();
            }
            String item = queue[head++];
            if (head == size) {
                head = 0;
            }
            count--;
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}
