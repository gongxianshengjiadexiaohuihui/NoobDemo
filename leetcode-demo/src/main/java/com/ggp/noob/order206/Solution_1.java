package com.ggp.noob.order206;

/**
 * @Author Created by gongguanpeng on 2022/2/22 8:45
 *
 */
public class Solution_1 {
    public ListNode reverseList(ListNode head) {
        //保存记录
        ListNode next = head.next;
        //head最后一定变成尾节点，所以next是null
        head.next=null;
        ListNode index;
        //开始逻辑
       while (null != next) {
           //index 往前走一
           index = next;
           //操作前保存一下记录
           next = next.next;

           //index变更为新head，且指向旧head
           index.next = head;
           //head更新
           head = index;
       }
       return head;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

