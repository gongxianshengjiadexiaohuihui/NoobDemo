package com.ggp.noob.demo.base.jni.type;

/**
 * @Author:ggp
 * @Date:2021/3/21 15:07
 * @Description:
 */
public class ObjectArray {
    static {
        System.loadLibrary("ObjectArray");
    }
    private static native int[][] initInt2DArray(int size);

    public static void main(String[] args) {
        int[][] i2array = initInt2DArray(3);
        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j <3 ; j++) {
                System.out.println(" "+ i2array[i][j]);
            }
            System.out.println();
        }
    }
}
