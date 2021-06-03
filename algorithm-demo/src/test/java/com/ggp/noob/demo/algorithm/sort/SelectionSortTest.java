package com.ggp.noob.demo.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author:ggp
 * @Date:2021/6/3 11:39
 * @Description:
 */
public class SelectionSortTest {

    @Test
    public void sort() {
        SelectionSort selectionSort = new SelectionSort();
        int[] src = new int[]{6,5,4,3,2,1};
        selectionSort.sort(src);
        for (int i = 0; i <src.length ; i++) {
            Assert.assertEquals(src[i],i+1);
        }
    }
}