package com.ggp.noob.demo.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @Author:ggp
 * @Date:2021/6/4 11:14
 * @Description:
 */
public class MergeSortTest {

    @Test
    public void sort() {
        MergeSort mergeSort = new MergeSort();
        int[] src = new int[]{6,5,4,3,2,1};
        mergeSort.sort(src);
        for (int i = 0; i <src.length ; i++) {
            Assert.assertEquals(src[i],i+1);
        }
    }
}