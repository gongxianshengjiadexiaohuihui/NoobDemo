package com.ggp.reference;

import java.lang.ref.WeakReference;

/**
 * @Author:GGP
 * @Date:2020/4/16 23:07
 * @Description:
 * 弱引用只要gc就会被回收，常用在容器中，比如ThreadLocal
 * @see com.ggp.juc.juc07_threadlocal
 */
public class R03_WeakRef {
    public static void main(String[] args) {
        WeakReference<Demo> m = new WeakReference<>(new Demo());
        System.out.println(m.get());
        System.gc();
    }
}
