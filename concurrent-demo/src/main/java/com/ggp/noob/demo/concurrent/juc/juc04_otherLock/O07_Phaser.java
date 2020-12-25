package com.ggp.noob.demo.concurrent.juc.juc04_otherLock;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/4 17:39
 * @Description:
 * 结合了countDownLatch和CyclicBarrier
 * 更灵活可以动态控制每个阶段的线程数
 */
public class O07_Phaser {
    static Random random = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();

    public static void main(String[] args) {
        phaser.bulkRegister(7);
        for (int i = 0; i <5 ; i++) {
            new Thread(new Person("visitor"+i)).start();
        }
        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();
    }
    static void sleep(int milli){
        try {
            TimeUnit.SECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class Person implements Runnable{

        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive(){
            sleep(random.nextInt(10));
            System.out.println(name+"到达现场");
            phaser.arriveAndAwaitAdvance();
        }
        public void eat(){
            sleep(random.nextInt(10));
            System.out.println(name+"吃完");
            phaser.arriveAndAwaitAdvance();
        }
        public void leave(){
            sleep(random.nextInt(10));
            System.out.println(name+"离开");
            phaser.arriveAndAwaitAdvance();
        }
        private void hug(){
            if(name.equals("新郎")||name.equals("新娘")){
                sleep(random.nextInt(10));
                System.out.println(name+"洞房");
                phaser.arriveAndAwaitAdvance();
            }else {
                phaser.arriveAndDeregister();
            }
        }
        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
}

static  class MarriagePhaser extends Phaser {
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                System.out.println("所有的人都到齐了！" + registeredParties);
                System.out.println();
                return false;
            case 1:
                System.out.println("所有的人都吃完了！" + registeredParties);
                System.out.println();
                return false;
            case 2:
                System.out.println("所有的人都离开了！" + registeredParties);
                System.out.println();
                return false;
            case 3:
                System.out.println("婚礼结束了！新郎新娘抱抱" + registeredParties);
                return true;
            default:
                return true;
        }
    }
}

}