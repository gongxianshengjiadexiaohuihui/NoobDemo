package com.ggp.noob.demo.algorithm.sort;

/**
 * @Author:ggp
 * @Date:2021/5/31 20:43
 * @Description: 插入排序
 */
public class InsertionSort {
    public void sort(int[] src) {
        for (int i = 1; i < src.length; i++) {
            int j = i - 1;
            int value = src[i];
            for (; j >= 0; j--) {
                //从小到大排列
                //遍历有序区，如果无序区的数小于有序区的某个数，就将该数往后移动一位
                if (src[j] > value) {
                    src[j + 1] = src[j];
                } else {
                    //大于的话就排在其后面
                    break;
                }
            }
            //遍历结束 j+1就是要插入的位置
            src[j+1] = value;
        }
    }
}
