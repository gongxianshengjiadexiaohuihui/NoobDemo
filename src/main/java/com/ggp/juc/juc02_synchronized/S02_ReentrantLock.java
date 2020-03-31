package com.ggp.juc.juc02_synchronized;

import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/3/31 20:16
 * @Description:
 * 调用m1的时候获得了this这个对象的锁，在m1里面又调用了m2，如果此时锁是不能获取，就会死锁，
 * 但是实验证明是 可以获取的，说明调用 m2的时候，它发现还是 这个线程，所以就 把锁给它了，所以
 * synchronized是可重入锁
 *
 */
public class S02_ReentrantLock {

    public static void main(String[] args) {
//        Demo4 t = new Demo4();
//        new Thread(()->{
//            t.m1();
//        }).start();
//        new Thread(()->{
//            t.m2();
//        }).start();
        /**
         * 父类和子类用的是一个this指针，子类继承了父类的所有属性，可以说包含了父类，只是调用父类的方法时用super访问，但是这些属性同属一个对象指针
         */
        Son son = new Son();
        new Thread(()->{
            son.m2();
        }).start();
        new Thread(()->{
            son.m();
        }).start();
    }
}
 class Demo4{
    synchronized void m1(){
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end");
    }
    synchronized void m2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }

}
class Father{
    synchronized void m(){
        System.out.println("father start");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("father end");
    }
}
class Son extends Father{
    void m2(){
        super.m();
    }
    @Override
    synchronized void m(){
        System.out.println("child start");
        super.m();
        System.out.println("child end");
    }
}