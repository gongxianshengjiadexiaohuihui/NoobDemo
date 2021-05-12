package com.ggp.noob.demo.algorithm.stack;

/**
 * @Author:ggp
 * @Date:2021/5/12 14:24
 * @Description: 顺序栈
 */
public class ArrayStack {
    private String[] stack;
    //栈顶元素下标
    private int top;
    private int size;

    public ArrayStack(int size) {
        this.stack = new String[size];
        this.size = size;
        this.top=0;
    }

    //入栈
    public boolean push(String item) {
        if (top == size) {
            //栈满
            return false;
        }
        stack[top++]=item;
        return true;
    }

    //出栈
    public String pop() {
        if(top==0){
            //栈空
            return null;
        }
        return stack[--top];
    }
    //打印
    public void print(){
        int temp = top;
        while (temp !=0){
            System.out.println(stack[--temp]);
        }
    }
}
