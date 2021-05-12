package com.ggp.noob.demo.algorithm.queue;

/**
 * @Author:ggp
 * @Date:2021/5/12 15:56
 * @Description:
 */
public class ArrayQueue {
    private String[] queue;
    private int head;
    private int tail;
    private int size;

    public ArrayQueue(int size) {
        this.size = size;
        queue = new String[size];
        this.head=0;
        this.tail=0;
    }
    public boolean enQueue(String item){
        if(tail == size){
            //队列满了
            return false;
        }
        queue[tail++]=item;
        return true;
    }
    public String deQueue(){
        if(head == tail){
            //队列为空
            return null;
        }
        return queue[head++];
    }
}
