package com.ggp.noob.demo.algorithm.queue;

import org.junit.Assert;
import org.junit.Test;


/**
 * @Author:ggp
 * @Date:2021/5/13 17:18
 * @Description:
 */
public class BlockingQueueTest {
    @Test
    public void test_FIFO() throws Exception{
        BlockingQueue queue = new BlockingQueue(5);
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        Assert.assertEquals(queue.dequeue(),"1");
        Assert.assertEquals(queue.dequeue(),"2");
        Assert.assertEquals(queue.dequeue(),"3");
        Assert.assertEquals(queue.dequeue(),"4");
        Assert.assertEquals(queue.dequeue(),"5");
    }
    @Test
    public void test_block_enqueue() throws Exception{
        BlockingQueue queue = new BlockingQueue(5);
        Thread t1 = new Thread(()->{
            try {
                queue.enqueue("1");
                queue.enqueue("2");
                queue.enqueue("3");
                queue.enqueue("4");
                queue.enqueue("5");
                queue.enqueue("6");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread.sleep(2000);
        Assert.assertTrue(t1.getState() == Thread.State.WAITING);
        queue.dequeue();
        Thread.sleep(2000);
        Assert.assertTrue(t1.getState() == Thread.State.TERMINATED);
    }
    @Test
    public void test_block_dequeue() throws Exception{
        BlockingQueue queue = new BlockingQueue(5);
        Thread t1 = new Thread(()->{
            try {
                Assert.assertEquals(queue.dequeue(),"1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread.sleep(2000);
        Assert.assertTrue(t1.getState() == Thread.State.WAITING);
        queue.enqueue("1");
        Thread.sleep(2000);
        Assert.assertTrue(t1.getState() == Thread.State.TERMINATED);
    }

}