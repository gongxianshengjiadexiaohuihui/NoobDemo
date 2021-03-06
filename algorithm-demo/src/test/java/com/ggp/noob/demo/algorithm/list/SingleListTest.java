package com.ggp.noob.demo.algorithm.list;

import org.junit.Test;

/**
 * @Author:ggp
 * @Date:2021/5/11 15:21
 * @Description:
 */
public class SingleListTest {
    //链表反转
    @Test
    public void test_list_reverse(){
        SingleList.Node node1 = new SingleList.Node(4, null);
        SingleList.Node node2 = new SingleList.Node(3, node1);
        SingleList.Node node3 = new SingleList.Node(2, node2);
        SingleList.Node node4 = new SingleList.Node(1, node3);
        SingleList singleList = new SingleList();
        SingleList.Node pre = singleList.reverse(node4);
        while (null != pre) {
            System.out.println(pre.data);
            pre = pre.next;
        }
    }
    //带头链表
    @Test
    public void test_sentinel_list(){
        SingleList.LinkList linkList = new SingleList.LinkList();
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
    //删除倒数第n个节点
    @Test
    public void test_delete_inverse_n(){
        SingleList.LinkList linkList = new SingleList.LinkList();
        //插入第一个节点
        linkList.insert(1,-1);
        linkList.insert(2,1);
        linkList.insert(3,2);
        linkList.insert(4,3);
        linkList.insert(5,4);
        linkList.print();
        System.out.println("删除倒数第二个节点");
        linkList.deleteInverseN(2);
        linkList.print();
    }
    //求链表中间节点
    @Test
    public void test_get_mid(){
        SingleList.LinkList linkList = new SingleList.LinkList();
        //插入第一个节点
        linkList.insert(1,-1);
        linkList.insert(2,1);
        linkList.insert(3,2);
        linkList.insert(4,3);
        linkList.insert(5,4);
        linkList.insert(6,5);
        linkList.print();
        System.out.println("中间节点");
        System.out.println(linkList.getMid().data);
    }

    @Test
    public void test_has_ring(){
        SingleList.LinkList linkList = new SingleList.LinkList();
        SingleList.Node node1 = new SingleList.Node(1,null);
        SingleList.Node node2 = new SingleList.Node(2,node1);
        SingleList.Node node3 = new SingleList.Node(3,node2);
        node1.next=node3;
        SingleList.Node node4 = new SingleList.Node(4,node3);
        linkList.sentinel.next = node4;
        System.out.println(linkList.hasRing());
    }
    @Test
    public void test_list_merage(){
        SingleList.Node node1 = new SingleList.Node(9, null);
        SingleList.Node node2 = new SingleList.Node(7, node1);
        SingleList.Node node3 = new SingleList.Node(4, node2);
        SingleList.Node node4 = new SingleList.Node(3, node3);

        SingleList.Node node5 = new SingleList.Node(8, null);
        SingleList.Node node6 = new SingleList.Node(6, node5);
        SingleList.Node node7 = new SingleList.Node(5, node6);
        SingleList.Node node8 = new SingleList.Node(2, node7);
        SingleList.Node node9 = new SingleList.Node(1, node8);
        SingleList singleList = new SingleList();
        SingleList.Node result = singleList.merge(node4,node9);
        while (null != result){
            System.out.println(result.data);
            result=result.next;
        }
    }
}