package com.ggp.noob.demo.io;

/**
 * @author: ggp
 * @Date: 2020/3/23 14:19
 * @Description:
 */
public class Calculator {
    public static int addCal(String expression){
        int index = expression.indexOf("+");
        int a = Integer.parseInt(expression.substring(0,index));
        int b = Integer.parseInt(expression.substring(index+1));
        return a+b;
    }

}
