package com.ggp.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.sql.SQLOutput;
import java.util.concurrent.*;

/**
 * @Author:GGP
 * @Date:2020/4/27 22:28
 * @Description:
 * 生产者模式有两种，一种是单线程，一种是多线程。
 * Producer.SINGLE  Producer.MULTI
 * 默认是MULTI
 * 单线程不加锁
 * 多线程加锁
 *
 * 消费者等待策略
 * BlockingWaitStrategy    通过线程阻塞的方式等待生产者唤醒，被唤醒后再循环检查依赖的sequence是否被消费
 * TimeOutBlockingWaitStrategy 与上面比设置了等待超齿时间，超出时间会抛出异常
 * BusySpinWaitStrategy    线程一直自旋等待，比较耗CPU
 * LiteBlockingWaitStrategy 线程阻塞等待，和BlockingWaitStrategy的却别在于signalNeeded.getAndSet,如果两个线程同时访问一个waitfor，一个访问signAll时，可以减少lock的次数
 * LiteTimeOutBlockingWaitStrategy  与上面比，设置了阻塞超时时间，如果超出了时间，会抛出异常
 * PhasedBackoffWaitStrategy  根据时间参数和传入的等待策略决定使用哪种等待策略
 *
 * 常用
 *   YieldingWaitStrategy 尝试100次，然后Thread.yield()让出cpu
 *   SleepingWaitStrategy sleep
 *
 */
public class T03_ProducerTypeAndWaitStrategy {
    public static void main(String[] args) throws Exception{
        LongEventFactory factory = new LongEventFactory();
        int bufferSize =1024;
       // Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,bufferSize,Executors.defaultThreadFactory());
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,bufferSize,Executors.defaultThreadFactory(),ProducerType.SINGLE,new BlockingWaitStrategy());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        final int threadCount=50;
        CyclicBarrier barrier = new CyclicBarrier(threadCount);
        ExecutorService service = Executors.newCachedThreadPool();
        for (long i = 0; i <threadCount ; i++) {
            final long threadNum = i;
            service.submit(()->{
            System.out.printf("thread %s ready to start\n",threadNum);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < 100; j++) {
                ringBuffer.publishEvent((event,sequence)->{
                    event.set(threadNum);
                    System.out.println("生产了"+threadNum);
                });
            }
            });
        }
        service.shutdown();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(LongEventHandler.count);
    }
}
