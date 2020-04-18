package com.ggp.container.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author:GGP
 * @Date:2020/4/18 22:08
 * @Description:
 */
public class Q01_ConCurrentLinkQueue {
    public static void main(String[] args) {
        Queue<String> str = new ConcurrentLinkedQueue<>();
        /**
         * 双向链表
         */
        //str = new ConcurrentLinkedDeque<>();
        for (int i = 0; i <10 ; i++) {
            /**
             * 头插法插入元素 CAS操作
             *  返回一个boolean 标识是否插入成功
             */
            str.offer("a"+i);
        }
        System.out.println(str);
        System.out.println(str.size());
        /**
         * 返回并移除头结点
         */
        System.out.println(str.poll());
        System.out.println(str.size());
        /**
         * 返回头结点
         */
        System.out.println(str.peek());
        System.out.println(str.size());
    }
}
