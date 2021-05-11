package com.ggp.noob.demo.algorithm.list;

/**
 * @Author:ggp
 * @Date:2021/5/11 09:48
 * @Description: 单链表反转
 */
public class SingleListInverse {
    static class Node {
        int data;
        Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

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

    public static void main(String[] args) {
        Node node1 = new Node(4, null);
        Node node2 = new Node(3, node1);
        Node node3 = new Node(2, node2);
        Node node4 = new Node(1, node3);
        SingleListInverse singleListInverse = new SingleListInverse();
        Node pre = singleListInverse.reverse(node4);
        while (null != pre) {
            System.out.println(pre.data);
            pre = pre.next;
        }

    }
}
