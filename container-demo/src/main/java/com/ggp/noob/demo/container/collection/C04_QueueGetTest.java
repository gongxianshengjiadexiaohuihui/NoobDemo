package com.ggp.noob.demo.container.collection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author:GGP
 * @Date:2020/4/17 23:25
 * @Description:
 * 实现是一个链表因此每次拿的时候，直接拿头然后删掉就可以了，因此效率极其高，高并发环境单个元素用queue.这是专门为高并发场景设计的
 */
public class C04_QueueGetTest {
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();
    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号"+i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                while (true) {
                    /**
                     * poll是返回头结点并移除（CAS）
                     * peek是检索不移除
                     */
                    String s = tickets.poll();
                    if(null == s){
                       break;
                    }
                    System.out.println(Thread.currentThread().getName()+"销售了"+s);
                }
            }).start();
        }
    }
}
