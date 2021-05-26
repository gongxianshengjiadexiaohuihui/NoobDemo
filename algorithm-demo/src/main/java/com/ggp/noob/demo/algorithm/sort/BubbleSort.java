package com.ggp.noob.demo.algorithm.sort;

/**
 * @Author:ggp
 * @Date:2021/5/26 19:10
 * @Description:
 * 冒泡排序
 */
public class BubbleSort {
    public void sort(int[] src){
       boolean flag = true;
       int temp=0;
        for (int i = 0; i < src.length; i++) {
            flag=true;
            for (int j = 0; j < src.length-i-1 ; j++) {
                if(src[j] >src[j+1]){
                 temp = src[j];
                 src[j] = src[j+1];
                 src[j+1] = temp;
                 flag=false;
                }
            }
            if(flag){
                //没有发生交换，已经是从小到大排列了
                break;
            }
        }
    }

}
