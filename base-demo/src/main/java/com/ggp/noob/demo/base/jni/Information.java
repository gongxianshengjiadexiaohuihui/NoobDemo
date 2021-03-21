package com.ggp.noob.demo.base.jni;

/**
 * @Author:ggp
 * @Date:2021/3/17 19:03
 * @Description:
 */
public class Information {
    static {
        System.loadLibrary("Information");
    }
    private native void name();
    private native void addr();

    public static void main(String[] args) {
        Information information = new Information();
        information.name();
        information.addr();
    }
}
