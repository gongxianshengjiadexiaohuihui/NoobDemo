package com.ggp.noob.demo.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:GGP
 * @Date:2020/3/25 19:21
 * @Description:
 */
public class AsyncClientHandler implements Runnable,CompletionHandler<Void,AsyncClientHandler> {
    private AsynchronousSocketChannel socketChannel;
    private String host;
    private int port;
    private CountDownLatch latch;
    public AsyncClientHandler(String ip, int port) {
        this.host = ip;
        this.port = port;
        try {
            socketChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        byte[] req = msg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        /**
         * 虽然是异步的但是用的是同一个socketChannel，避免一个正在写的时候，另一个又要写入
         */
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketChannel.write(writeBuffer,writeBuffer,new WriterHandler(socketChannel,latch));
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        /**
         * 发起异步连接，回调参数就是这个类本身，如果连接成功就调用自身的complete方法
         */
        socketChannel.connect(new InetSocketAddress(host,port),this,this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncClientHandler attachment) {
        System.out.println("客户端连接到服务器...");
    }

    @Override
    public void failed(Throwable exc, AsyncClientHandler attachment) {
        System.out.println("客户端连接服务器失败");
        exc.printStackTrace();
        latch.countDown();
    }
}
