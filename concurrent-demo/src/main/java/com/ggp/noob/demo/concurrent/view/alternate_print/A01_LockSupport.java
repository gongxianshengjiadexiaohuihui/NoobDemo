package com.ggp.noob.demo.concurrent.view.alternate_print;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author:GGP
 * @Date:2020/4/19 18:30
 * @Description:
 * 利用LockSupport，每个线程执行完阻塞并唤醒另一个线程执行
 */
public class A01_LockSupport {
    static Thread t1 = null,t2 = null;

    public static void main(String[] args) {
        t1 = new Thread(()->{
            for (int i = 0; i < Constant.num.length; i++) {
                System.out.println(Constant.num[i]);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(()->{
            for (int i = 0; i <Constant.letter.length ; i++) {
                LockSupport.park();
                System.out.println(Constant.letter[i]);
                LockSupport.unpark(t1);
            }
        });
        t1.start();
        t2.start();
    }
}
