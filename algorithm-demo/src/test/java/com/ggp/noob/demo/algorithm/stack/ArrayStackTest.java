package com.ggp.noob.demo.algorithm.stack;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author:ggp
 * @Date:2021/5/12 14:47
 * @Description:
 */
public class ArrayStackTest {
    @Test
    public void test_border() {
        ArrayStack stack = new ArrayStack(5);
        Assert.assertNull(stack.pop());
        Assert.assertTrue(stack.push("1"));
        Assert.assertTrue(stack.push("2"));
        Assert.assertTrue(stack.push("3"));
        Assert.assertTrue(stack.push("4"));
        Assert.assertTrue(stack.push("5"));
        Assert.assertFalse(stack.push("6"));
    }

    @Test
    public void test_base_function() {
        ArrayStack stack = new ArrayStack(5);
        stack.push("1");
        stack.push("2");
        stack.push("3");
        Assert.assertEquals(stack.pop(),"3");
        Assert.assertEquals(stack.pop(),"2");
        stack.push("4");
        Assert.assertEquals(stack.pop(),"4");
        Assert.assertEquals(stack.pop(),"1");
    }

}