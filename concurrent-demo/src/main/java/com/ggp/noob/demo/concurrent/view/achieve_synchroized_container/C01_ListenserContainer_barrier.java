package com.ggp.noob.demo.concurrent.view.achieve_synchroized_container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author:GGP
 * @Date:2020/4/5 15:09
 * @Description:
 */
public class C01_ListenserContainer_barrier {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);
        Container1 list = new Container1();
        new Thread(()->{
            for (int i = 1; i <11 ; i++) {
                list.add(i);
                if(i == 5){
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ).start();
        new Thread(()->{
            while (true){
                if(list.size() == 5){
                    System.out.println("数量到达5");
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            System.out.println("t2结束");
        }).start();
    }
}
class Container1{
    private List<Integer> list = new ArrayList<>();
    private int count = 0;
    public synchronized void add(Integer i){
        list.add(i);
        count++;
        System.out.println("add"+i);
    }
    public synchronized int size(){
        return count;
    }
}
