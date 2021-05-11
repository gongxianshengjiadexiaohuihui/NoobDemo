package com.ggp.noob.demo.algorithm.list;

/**
 * @Author:ggp
 * @Date:2021/5/11 09:48
 * @Description:
 * 单链表反转
 *
 * 带头链表，又称哨兵链表
 * 哨兵的作用是----------简化边界
 * 在循环次数很多的场景下，能在每次循环中都简化一次边界判断
 *
 * 删除链表倒数第n个节点--快慢指针
 */
public class SingleList {
    static class Node {
        int data;
        Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    static class LinkList {

        private Node sentinel;

        public LinkList() {
            //哨兵数据放-1，作为边界值(这里只是做实验)
            this.sentinel = new Node(-1, null);
        }

        //在目标节点后插入节点
        public boolean insert(int value, int position) {
            Node head = sentinel;
            //找到目标节点，哨兵省去了判断插入节点为第一个节点的情况，也就是position为-1
            while (head.data != position) {
                head = head.next;
                if (null == head) {
                    //这里的边界依然可以采用哨兵的方式，但是需要遍历，数组的情况可以考虑
                    return false;
                }
            }
            //插入操作
            Node node = new Node(value, null);
            node.next = head.next;
            head.next = node;
            return true;
        }

        //删除某节点
        public boolean delete(int value) {
            Node head = sentinel.next;
            Node pre = sentinel;
            while (head.data != value) {
                pre = head;
                head = head.next;
                if (null == head) {
                    //这里的边界依然可以采用哨兵的方式，但是需要遍历，数组的情况可以考虑
                    return false;
                }
            }
            //删除该节点
            pre.next = pre.next.next;
            return true;
        }
        //删除倒数第n个节点  快慢指针
        public boolean deleteInverseN(int n){
            Node fast = sentinel.next;
            Node slow = sentinel.next;
            //因为是单链表，要记录要删除节点的前置节点
            Node pre =null;
            int i=1;
            //快指针先走到n
            while (i<n){
                fast =fast.next;
                i++;
            }
            //快指针和慢指针一起走，快指针都到终点，慢指针刚好走到倒数第n个节点
            while (null !=fast.next){
                fast =fast.next;
                pre=slow;
                slow =slow.next;
            }
            //此时slow就是要找的节点
            pre.next = pre.next.next;
            return true;
        }

        //链表打印
        public void print() {
            Node head = sentinel.next;
            while (null != head) {
                System.out.println(head.data);
                head = head.next;
            }
        }
    }

    //链表反转
    public Node reverse(Node head) {
        //反转链表头指针
        Node pre = null;
        Node temp;
        while (null != head) {
            //保存现场
            temp = head.next;
            //反转
            head.next = pre;
            pre = head;
            //恢复现场
            head = temp;
        }
        return pre;
    }

}
