package com.ggp.noob.demo.concurrent.view.alternate_print;

/**
 * @Author:GGP
 * @Date:2020/4/19 21:17
 * @Description:
 *  模仿CAS,线程打印完后尝试去修改r的值让另外一个线程去执行，
 *  所有线程不停读取r的值，直到这个值是自己执行的情况，停止自旋
 */
public class A04_CAS {
    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T1;

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < Constant.num.length ; i++) {
                while (r != ReadyToRun.T1){}
                    System.out.println(Constant.num[i]);
                    r = ReadyToRun.T2;
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i <Constant.letter.length ; i++) {
                while (r!=ReadyToRun.T2){}
                    System.out.println(Constant.letter[i]);
                    r = ReadyToRun.T1;
            }
        }).start();
    }
}
