package com.ggp.noob.demo.base.jni.type;

/**
 * @Author:ggp
 * @Date:2021/3/21 14:41
 * @Description:
 */
public class IntArray {
    static {
        System.loadLibrary("IntArray");
    }
    private native int sumArray(int[] arr);

    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i <10 ; i++) {
            arr[i]=i;
        }
        IntArray intArray = new IntArray();
        int sum = intArray.sumArray(arr);
        System.out.println(sum);
    }

}
