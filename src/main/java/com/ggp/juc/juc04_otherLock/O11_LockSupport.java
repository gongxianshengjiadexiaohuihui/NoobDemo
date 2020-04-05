package com.ggp.juc.juc04_otherLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author:GGP
 * @Date:2020/4/4 23:34
 * @Description:
 */
public class O11_LockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                System.out.println(i);
                if(i == 5){
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        //LockSupport.unpark(t);
    }
}
