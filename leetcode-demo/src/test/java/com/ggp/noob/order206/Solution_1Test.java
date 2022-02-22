package com.ggp.noob.order206;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author Created by gongguanpeng on 2022/2/22 15:43
 */
public class Solution_1Test {
    @Test
    public void test_critical() {
        ListNode head = new ListNode(1);
        Solution_1 solution = new Solution_1();
        head = solution.reverseList(head);
        while (null != head){
            System.out.println(head.val);
            head = head.next;
        }
    }
    @Test
    public void test(){
        ListNode head = new ListNode(1);
        ListNode node1 = new ListNode(2);
        head.next = node1;
        ListNode node2 = new ListNode(3);
        node1.next = node2;
        ListNode node3 = new ListNode(4);
        node2.next = node3;
        ListNode node4 = new ListNode(5);
        node3.next = node4;
        Solution_1 solution = new Solution_1();
        head = solution.reverseList(head);
        while (null != head){
            System.out.println(head.val);
            head = head.next;
        }
    }
}