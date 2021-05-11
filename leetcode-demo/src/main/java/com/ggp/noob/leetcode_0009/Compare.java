package com.ggp.noob.leetcode_0009;

/**
 * @Author:ggp
 * @Date:2021/5/10 20:58
 * @Description:
 */
public class Compare {
    public boolean isPalindrome(int x) {
        if(x<0){
            return false;
        }
        int help =1;
        int num =x;
        //计算最高位的10的阶注意是从10的0次方开始
        while (num/10!=0){
            help=help*10;
            num/=10;
        }
        //从两头开始比较 x/help是第一位，x%10是最后一位
        while (x!=0){
            if(x/help != x%10){
                return false;
            }
            //去掉第一位和最后一位
            x=x%help/10;
            //去了两位所以去两阶
            help=help/100;
        }
        return true;
    }

    public static void main(String[] args) {
        Compare compare = new Compare();
        System.out.println(compare.isPalindrome(121));
    }
}
