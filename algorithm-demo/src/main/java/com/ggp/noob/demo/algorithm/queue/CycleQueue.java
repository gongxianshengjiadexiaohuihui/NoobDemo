package com.ggp.noob.demo.algorithm.queue;

/**
 * @Author:ggp
 * @Date:2021/5/13 11:27
 * @Description: 循环队列
 */
public class CycleQueue {
    public String[] queue;
    public int head;
    public int tail;
    public int size;

    public CycleQueue(int size) {
        this.size = size;
        queue = new String[size];
        this.head = 0;
        this.tail = 0;
    }
    //入队
    public boolean enQueue(String item) {
        //之所以要取余是为了应对tail在size,head在0这种情况
        //循环队列会空一个单位(永远不会满)，不然无法区分循环队列队空和队满
        if ((tail + 1) % size == head) {
            //队满
            return false;
        }
        queue[tail++] = item;
        if (tail == size) {
            //构成循环
            tail = 0;
        }
        return true;
    }
    //出队
    public String deQueue() {
        if (tail == head) {
            //队空
            return null;
        }
        String item = queue[head++];
        if (head == size) {
            //构成循环
            head = 0;
        }
        return item;
    }
}
