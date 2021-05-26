package com.ggp.noob.demo.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @Author:ggp
 * @Date:2021/5/26 20:09
 * @Description:
 */
public class BubbleSortTest {

    @Test
    public void sort() {
        BubbleSort bubbleSort = new BubbleSort();
        int[] src = new int[]{6,5,4,3,2,1};
        bubbleSort.sort(src);
        for (int i = 0; i <src.length ; i++) {
            Assert.assertEquals(src[i],i+1);
        }
    }
}