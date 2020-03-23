package com.ggp.io.nio;

import java.util.Random;

/**
 * @Author:GGP
 * @Date:2020/3/23 22:39
 * @Description:
 */
public class Main {
    public static void main(String[] args)throws Exception {
        Server.start();
        Thread.sleep(100);
        Client.start();
        Thread.sleep(100);
        Random random = new Random();
        int i =0;
        while (i != 100){
            i++;
            int a = random.nextInt(100);
            int b = random.nextInt(100);
           Client.send(a+"+"+b);
        }
    }
}
