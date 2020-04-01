package com.ggp.juc.juc03_volatile;

import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/3/31 23:34
 * @Description:
 */
public class V01_HelloVolatile {
    /**
     * jvm对热点代码有两种执行方式，一种是编译执行，一种是解释执行，对于解释执行，下面例子很难区分加不加volatile的区别
     * 所以需要设置编译执行  加虚拟机参数 -server 或-Xcomp
     * 下面的例子主要体现volatile保证线程的可见性
     * @param args
     */
    public static void main(String[] args) {
        Demo1 t = new Demo1();
        new Thread(()->{
            t.m();
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running =false;
    }
}
class Demo1{
     /*volatile*/ boolean running = true;
    void m(){
        System.out.println("m start");
        int i=1;
        while (running){
        }
        System.out.println("m end!");
    }
}
