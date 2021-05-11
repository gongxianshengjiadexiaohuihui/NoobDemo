package com.ggp.noob.leetcode_0009;

/**
 * @Author:ggp
 * @Date:2021/5/10 14:57
 * @Description: 单链表-快慢指针法
 */
public class SingleList {
    class Node {
        int data;
        Node next;
    }

    //将数字解析为链表  这里是倒序的，怎么解析成正序？
    public Node parse(int x) {
        Node node, head, temp;
        node = new Node();
        head = node;
        temp = head;
        while (x >= 10) {
            node.data = x % 10;
            temp.next = node;
            node = new Node();
            temp = temp.next;
            x = x / 10;
        }
        node.data = x;
        temp.next = node;
        return head;
    }

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x < 10) {
            return true;
        }
        Node head = parse(x);
        Node temp, pre = null, fast = head, slow = head;
        //标识x是奇位数还是偶位数
        boolean flag;
        while (true) {
            //快指针每次走两步，必须快指针先走，不然会被慢指针打乱
            if (null == fast.next) {
                //奇数
                flag = false;
                break;
            }
            if (null == fast.next.next) {
                //偶数 此时慢指针位于上中间数
                flag = true;
                break;
            }
            fast = fast.next.next;

            //慢指针每次走一步，并逆序
            //保存下一个节点
            temp = slow.next;
            //让下一个节点指向前节点--逆序
            slow.next = pre;
            //让逆序链表的头指向最新节点
            pre = slow;
            //恢复现场,并走一步
            slow = temp;


        }
        if (null == pre) {
            //两个数才会出现这个情况
            if (slow.data == slow.next.data) {
                return true;
            }
            return false;
        }
        //快指针走完时，慢指针刚好走到中间位置
        if (flag) {
            //如果中间两个数不等，说明不符合条件
            if (slow.data != slow.next.data) {
                return false;
            }
            slow = slow.next.next;
        } else {
            slow = slow.next;
        }
        //do while是防止3位数的情况，因此先执行一次
        do {
            if (pre.data != slow.data) {
                return false;
            }
            pre = pre.next;
            slow = slow.next;
        } while (null != pre);
        return true;
    }

    public static void main(String[] args) {
        int x = 121;
        SingleList singleList = new SingleList();
        Node head = singleList.parse(x);
        while (null != head.next) {
            System.out.println(head.data);
            head = head.next;
        }
        System.out.println(head.data);
        System.out.println(singleList.isPalindrome(x));
    }
}
