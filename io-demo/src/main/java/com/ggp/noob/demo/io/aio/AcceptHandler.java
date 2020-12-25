package com.ggp.noob.demo.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author:GGP
 * @Date:2020/3/24 20:25
 * @Description:
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncServerHandle> {
    /**
     *  通道完成io工作后，会调用下面这个回调函数，回调函数要实现CompletionHandler<V,A>,其中V是IO操作的结果，A是调用回调函数需要传入的参数
     * @param channel
     * @param serverHandler
     */
    @Override
    public void completed(AsynchronousSocketChannel channel, AsyncServerHandle serverHandler) {
        Server.clientCount++;
        System.out.println("连接的客户端数："+Server.clientCount);
        /**
         * 因为accept是非阻塞的，所以循环调用，一直接受客户端信息。
         */
        serverHandler.channel.accept(serverHandler,this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer,buffer,new ServerReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AsyncServerHandle serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }
}
