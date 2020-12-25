package com.ggp.noob.demo.io.aio;

import java.util.Random;

/**
 * @Author:GGP
 * @Date:2020/3/25 19:48
 * @Description:
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server.start();
        Thread.sleep(1000);
        Client.start();
        Random random = new Random();
        int i =0;
        while (i != 100){
            i++;
            int a = random.nextInt(100);
            int b = random.nextInt(100);
            Client.sendMsg(a+"+"+b);
        }
    }
}
