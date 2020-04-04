package com.ggp.juc.juc04_otherLock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author:GGP
 * @Date:2020/4/4 17:32
 * @Description:
 * 可以灵活的让几个线程进行了某种操作后，同时进行下一步，或者执行某个动作
 */
public class O06_CyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20,()->{
            System.out.println("满人，发车");
        });
        for (int i = 0; i <100 ; i++) {
            new Thread(()->{
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }
}

