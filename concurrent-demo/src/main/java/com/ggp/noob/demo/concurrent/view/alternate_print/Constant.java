package com.ggp.noob.demo.concurrent.view.alternate_print;

/**
 * @Author:GGP
 * @Date:2020/4/19 18:32
 * @Description:
 */
public class Constant {
    static int[] num = new int[26];
    static char[] letter = new char[26];
    static {
        for (int i = 0; i < 26; i++) {
            num[i] = i+1;
            letter[i] =(char)(65+i);
        }
    }
}
