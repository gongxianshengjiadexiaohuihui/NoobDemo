package com.ggp.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:GGP
 * @Date:2020/3/25 21:08
 * @Description:
 */
public class Client {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT=12345;
    private static CountDownLatch latch;
    public static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }
    public static void start(String host,int port){
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap.group(worker);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.option(ChannelOption.SO_KEEPALIVE,true);

        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ClientHandler());
            }
        });

        try {
            ChannelFuture future= clientBootstrap.connect(host,port).sync();
            System.out.println("客户端连接成功");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
