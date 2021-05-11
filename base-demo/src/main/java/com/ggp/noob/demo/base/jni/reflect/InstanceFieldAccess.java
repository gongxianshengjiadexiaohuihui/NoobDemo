package com.ggp.noob.demo.base.jni.reflect;

/**
 * @Author:ggp
 * @Date:2021/3/22 10:51
 * @Description:
 */
public class InstanceFieldAccess {
    static {
        System.loadLibrary("InstanceFieldAccess");
    }
    private String s;
    private native void accessField();

}
