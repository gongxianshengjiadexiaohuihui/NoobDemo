package com.ggp.reference;

/**
 * @Author:GGP
 * @Date:2020/4/16 22:23
 * @Description:
 * 只要对象被一个引用指向，就永远不会被回收，
 */
public class R01_StrongRef {
    public static void main(String[] args) throws Exception {
        Demo t = new Demo();
//        t = null;
        System.gc();
        /**
         * 阻塞线程
         */
        System.in.read();
    }
}
class Demo{
    protected void finalize() throws Exception{
        System.out.println("finalize");
    }
}
