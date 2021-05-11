package com.ggp.noob.leetcode_0009;

/**
 * @Author:ggp
 * @Date:2021/5/10 20:49
 * @Description:
 * 求出逆序的值，然后比较
 */
public class InverseValue {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int inverse = 0;
        int num = x;
        while (num != 0){
            inverse = inverse*10+num%10;
            num=num/10;
        }
        return inverse == x;
    }

    public static void main(String[] args) {
        InverseValue inverseValue = new InverseValue();
        System.out.println(inverseValue.isPalindrome(10));
    }
}
