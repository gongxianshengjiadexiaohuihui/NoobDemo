package com.ggp.noob.leetcode_0009;

/**
 * @Author:ggp
 * @Date:2021/5/10 20:35
 * @Description:
 * 暴力解法
 */
public class Violence {
    public boolean isPalindrome(int x) {
        String s = new StringBuilder(x+"").reverse().toString();
        return s.equals(x+"");
    }

    public static void main(String[] args) {
        Violence violence = new Violence();
        System.out.println(violence.isPalindrome(12321));
    }
}
