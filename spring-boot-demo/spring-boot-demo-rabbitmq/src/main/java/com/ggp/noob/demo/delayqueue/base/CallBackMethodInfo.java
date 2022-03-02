package com.ggp.noob.demo.delayqueue.base;

/**
 * @Author Created by gongguanpeng on 2022/3/2 15:25
 */
public class CallBackMethodInfo {
    /**
     * 请求参数类型--全限定名
     */
    private String paramClassName;
    /**
     * 调用者类型--全限定名
     */
    private String caller;
    /**
     * 调用的方法名
     */
    private String methodName;

    /**
     * 回调方法
     */
    private byte[] callback;

    public String getParamClassName() {
        return paramClassName;
    }

    public void setParamClassName(String paramClassName) {
        this.paramClassName = paramClassName;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public byte[] getCallback() {
        return callback;
    }

    public void setCallback(byte[] callback) {
        this.callback = callback;
    }
}
