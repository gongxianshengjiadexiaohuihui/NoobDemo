package com.ggp.container.collection;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author:GGP
 * @Date:2020/4/17 22:42
 * @Description:
 * ArrayList是线程不安全的，因为size属性是不同线程是不可见的
 * 要想看到这个现象需要设置虚拟机参数-Xint或者-client  因为虚拟机会自动优化
 */
public class C01_ListGetTest {
    static List<String> tikets = new ArrayList<>();
    static {
        for (int i = 0; i < 15; i++) {
            tikets.add("票编号"+i);
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i <3 ; i++) {
            new Thread(()->{
                while (tikets.size()>0){
                    /**
                     * arrayList remove一个数据还会把数组整体往前移动一个单位
                     */
                    System.out.println(Thread.currentThread().getName()+"销售了"+tikets.remove(0));

                }
            }).start();
        }
    }
}
