package com.ggp.noob.demo.base.jni;

/**
 * @Author:ggp
 * @Date:2021/3/17 10:37
 * @Description:
 */
public class JniHelloWorld {
    static {
        System.loadLibrary("JniHelloWorld");
    }

    public native void sayHelloWorld();

    public static void main(String[] args) {
        JniHelloWorld jniHelloWorld = new JniHelloWorld();
        jniHelloWorld.sayHelloWorld();
    }
}
