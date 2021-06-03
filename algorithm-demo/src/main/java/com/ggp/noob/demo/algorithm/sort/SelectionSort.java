package com.ggp.noob.demo.algorithm.sort;

/**
 * @Author:ggp
 * @Date:2021/6/3 11:31
 * @Description:
 */
public class SelectionSort {
    public void sort(int[] src){
        int min_index=0;
        int temp=0;
        for (int i = 0; i <src.length-1; i++) {
            min_index=i;
            //每次从未排序区间中取出最小值的下标
            for (int j = i+1; j < src.length; j++) {
                if(src[i]>src[j]){
                    min_index = j;
                }
            }
            //将最小值与已排序区间的末尾交换
            temp = src[i];
            src[i] = src[min_index];
            src[min_index]=temp;
        }
    }
}
