package com.ggp.noob.demo.algorithm.queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author:ggp
 * @Date:2021/5/12 20:04
 * @Description:
 */
public class ArrayQueueTest {
    @Test
    public void test_border(){
        ArrayQueue queue = new ArrayQueue(5);
        Assert.assertNull(queue.deQueue());
        Assert.assertTrue(queue.enQueue("1"));
        Assert.assertTrue(queue.enQueue("2"));
        Assert.assertTrue(queue.enQueue("3"));
        Assert.assertTrue(queue.enQueue("4"));
        Assert.assertTrue(queue.enQueue("5"));
        Assert.assertFalse(queue.enQueue("6"));
    }
    @Test
    public void test_FIFO(){
        ArrayQueue queue = new ArrayQueue(5);
        queue.enQueue("1");
        queue.enQueue("2");
        queue.enQueue("3");
        queue.enQueue("4");
        queue.enQueue("5");
        Assert.assertEquals(queue.deQueue(),"1");
        Assert.assertEquals(queue.deQueue(),"2");
        Assert.assertEquals(queue.deQueue(),"3");
        Assert.assertEquals(queue.deQueue(),"4");
        Assert.assertEquals(queue.deQueue(),"5");
    }
}