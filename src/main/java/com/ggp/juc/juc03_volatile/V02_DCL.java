package com.ggp.juc.juc03_volatile;


/**
 * @Author:GGP
 * @Date:2020/4/1 20:30
 * @Description:
 */
public class V02_DCL {
    public static void main(String[] args) {
        /**
         * new 一个对象需要四条指令,比如下面这行代码一共用了4条指令
         * 1    NEW java/lang/Object    申请内存拿到一个指向地址的指针
         * 2   DUP                     复制操作栈顶元素，此时操作栈顶有两个相同指针
         * 3    INVOKESPECIAL java/lang/Object.<init> ()V 构造函数需要消耗一个指针，因为没有返回值
         * 4   ASTORE 1                 保存栈顶指针到局部变量表（让o指向实例）
         */
        Object o= new Object();
        /**
         *  cpu为了提高速度可能并行执行指令1，所以存在4 跑到1后面，而且不影响最终结果
         *   加了volatile后会禁止这种排序（实现原理内存屏障，cpu有对应原语）
         */
    }
}
class Demo2{
    private volatile static Demo2 INSTANCE ;
    private Demo2(){}
    public static Demo2 getInstance(){
        if(null == INSTANCE){
            synchronized (Demo2.class) {
                if(null !=INSTANCE){
                    INSTANCE = new Demo2();
                }
            }
        }
        return INSTANCE;
    }
}
