package com.ggp.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author:GGP
 * @Date:2020/3/24 20:25
 * @Description:
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncServerHandle> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AsyncServerHandle serverHandler) {
        Server.clientCount++;
        System.out.println("连接的客户端数："+Server.clientCount);
        serverHandler.channel.accept(serverHandler,this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer,buffer,new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AsyncServerHandle serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }
}
