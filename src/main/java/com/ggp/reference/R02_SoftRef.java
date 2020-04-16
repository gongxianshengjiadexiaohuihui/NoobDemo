package com.ggp.reference;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/16 22:40
 * @Description:
 * 软引用只有在内存不足的时候才会被回收
 * 做这个实验需要设置堆的大小为20M
 */
public class R02_SoftRef {
    public static void main(String[] args) {
        /**
         * 这个m指向的是一个堆里的一个软引用对象，这个软引用对象里面又持有一个引用指向一个10M的数组对象
         */
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println(m.get());
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 又申请15M,这时候内存是不够用的，就会回收m中的对象
         */
        byte[] b= new byte[1024*1024*15];
        System.out.println(m.get());
    }
}
/**
 * 软引用常用作缓存，比如一张图片非常大，一段时间不用可以被回收，用的时候再去取
 */
