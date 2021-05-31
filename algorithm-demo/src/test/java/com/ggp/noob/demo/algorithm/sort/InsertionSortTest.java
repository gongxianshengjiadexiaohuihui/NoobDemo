package com.ggp.noob.demo.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @Author:ggp
 * @Date:2021/5/31 21:08
 * @Description:
 */
public class InsertionSortTest {


    @Test
    public void sort() {
        InsertionSort insertionSort = new InsertionSort();
        int[] src = new int[]{6,5,4,3,2,1};
        insertionSort.sort(src);
        System.out.println(Arrays.toString(src));
        for (int i = 0; i <src.length ; i++) {
            Assert.assertEquals(src[i],i+1);
        }
    }
}