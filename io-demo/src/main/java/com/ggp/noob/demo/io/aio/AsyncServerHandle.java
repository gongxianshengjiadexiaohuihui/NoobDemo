package com.ggp.noob.demo.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:GGP
 * @Date:2020/3/24 20:14
 * @Description:
 */
public class AsyncServerHandle implements Runnable{
    public CountDownLatch latch;
    public AsynchronousServerSocketChannel channel;
    public AsyncServerHandle(int port) {
        try {
            /**
             * 创建服务端通道
             */
            channel = AsynchronousServerSocketChannel.open();
            channel.bind(new InetSocketAddress(port));
            System.out.println("服务已经启动，端口号为："+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        /**
         * 因为下面的accept方法是非阻塞的，为了不然程序结束，让当前线程挂起，如果出现异常调用countDown方法，让程序结束
         */
        latch =new CountDownLatch(1);
        channel.accept(this,new AcceptHandler());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
