package com.ggp.noob.demo.algorithm.list;

/**
 * @Author:ggp
 * @Date:2021/5/11 14:00
 * @Description:
 * 带头链表，又称哨兵链表
 * 哨兵的作用是----------简化边界
 * 在循环次数很多的场景下，能在每次循环中都简化一次边界判断
 */
public class SentinelSingleList {
    static class Node{
        int data;
        Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
    static class LinkList{

        private Node sentinel;

        public LinkList() {
            //哨兵数据放-1，作为边界值(这里只是做实验)
            this.sentinel = new Node(-1,null);
        }
        //在目标节点后插入节点
        public boolean insert(int value,int position){
            Node head =sentinel;
            //找到目标节点，哨兵省去了判断插入节点为第一个节点的情况，也就是position为-1
            while (head.data !=position){
                head=head.next;
                if(null == head){
                    //这里的边界依然可以采用哨兵的方式，但是需要遍历，数组的情况可以考虑
                    return false;
                }
            }
            //插入操作
            Node node = new Node(value,null);
            node.next = head.next;
            head.next=node;
            return true;
        }
        //删除某节点
        public boolean delete(int value){
            Node head = sentinel.next;
            Node pre = sentinel;
            while (head.data != value){
                pre=head;
                head = head.next;
                if(null == head){
                    //这里的边界依然可以采用哨兵的方式，但是需要遍历，数组的情况可以考虑
                    return false;
                }
            }
            //删除该节点
            pre.next=pre.next.next;
            return true;
        }
        public void print(){
            Node head = sentinel.next;
            while (null != head){
                System.out.println(head.data);
                head = head.next;
            }
        }
    }

    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        //插入第一个节点
        linkList.insert(1,-1);
        linkList.insert(2,1);
        linkList.insert(3,2);
        linkList.print();
        System.out.println("删除3");
        linkList.delete(3);
        linkList.print();
        System.out.println("删除2");
        linkList.delete(2);
        linkList.print();
        System.out.println("删除1");
        linkList.delete(1);
        linkList.print();
    }
}
