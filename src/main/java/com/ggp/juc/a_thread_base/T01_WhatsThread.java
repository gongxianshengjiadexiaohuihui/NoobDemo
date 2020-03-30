package com.ggp.juc.a_thread_base;

/**
 * @Author:GGP
 * @Date:2020/3/30 20:08
 * @Description: 线程是程序的不同执行路径
 */
public class T01_WhatsThread {
    private static class T1 extends Thread{
        @Override
        public void run(){
            System.out.println("T1");
        }
    }

    public static void main(String[] args) {
        /**
         * 方法调用
         */
        new T1().run();
        /**
         * 线程启动
         */
        //new T1().start();
        for (int i = 0; i <10 ; i++) {
            System.out.println("Main");
        }
    }
}
