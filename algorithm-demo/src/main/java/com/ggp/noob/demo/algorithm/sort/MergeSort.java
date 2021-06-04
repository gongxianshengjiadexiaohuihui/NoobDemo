package com.ggp.noob.demo.algorithm.sort;

/**
 * @Author:ggp
 * @Date:2021/6/3 14:42
 * @Description: 归并排序
 * 递推公司
 * divide(src[r...s])=divide(src[r...(r+s)/2])+divide(src[(r+s)/2+1...s])
 * 终止条件：r>=s
 */
public class MergeSort {
    public void sort(int[] src) {
        divide(src,0,src.length-1);
    }

    //分
    public void divide(int[] src, int r, int s) {
        if (r >= s) {
            return;
        }
        int mid = (r + s) / 2;
        if(mid==0){
            mid = r;
        }
        divide(src, r, mid);
        divide(src, mid + 1, s);
        merge(src, r, s, mid);
    }

    //合
    private void merge(int[] src, int r, int s, int mid) {
        int[] temp = new int[s - r + 1];
        int i = 0;
        int r_index=r;
        int s_index = mid + 1;
        //将两个有序数组合并
        while (r_index <= mid && s_index <= s) {
            if (src[r_index] < src[s_index]) {
                temp[i++] = src[r_index++];
            } else {
                temp[i++] = src[s_index++];
            }
        }
        if (r_index <= mid) {
            while (r_index <= mid) {
                temp[i++] = src[r_index++];
            }
        }
        if (s_index <= s) {
            while (s_index <= s) {
                temp[i++] = src[s_index++];
            }
        }
        //将合并后的结果放入原数组
        for ( i = 0; i < temp.length ; i++) {
            src[r+i]=temp[i];
        }
    }
}
