package com.ggp.noob.demo.algorithm.queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author:ggp
 * @Date:2021/5/13 11:42
 * @Description:
 */
public class CycleQueueTest {
    @Test
    public void test_border(){
        CycleQueue queue = new CycleQueue(5);
        Assert.assertNull(queue.deQueue());
        queue.enQueue("1");
        queue.deQueue();
        Assert.assertNull(queue.deQueue());
        queue.enQueue("1");
        queue.enQueue("2");
        queue.enQueue("3");
        queue.enQueue("4");
        queue.enQueue("5");
        Assert.assertFalse(queue.enQueue("6"));
    }
    @Test
    public void test_cycle(){
        CycleQueue queue = new CycleQueue(5);
        queue.enQueue("1");
        queue.enQueue("2");
        queue.enQueue("3");
        queue.enQueue("4");
        queue.enQueue("5");
        Assert.assertEquals(queue.deQueue(),"1");
        Assert.assertTrue(queue.enQueue("6"));
    }

}