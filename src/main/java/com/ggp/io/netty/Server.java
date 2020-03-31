package com.ggp.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author:GGP
 * @Date:2020/3/25 20:18
 * @Description:
 */
public class Server {
    private static int DEFAULT_PORT = 12345;

    public static void start(){
        start(DEFAULT_PORT);
    }
    public static void start(int port){
        /**
         * 创建两个事件循环组，NioEventLoopGroup是一个处理I/O操作的多线程事件循环。
         * Netty为不同类型的传输提供了各种EventLoopGroup实现。
         * 在本例中，我们正在实现一个服务器端应用程序，因此将使用两个NioEventLoopGroup。
         * 第一个，通常称为“boss”，接受传入的连接。
         * 第二个，通常称为“worker”，当boss接受连接并注册被接受的连接到worker时，处理被接受连接的流量。
         * 使用了多少线程以及如何将它们映射到创建的通道取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
         */
        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        /**
         * 创建启动类
         */
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        /**
         *  Set the {@link EventLoopGroup} for the parent (acceptor) and the child (client)
         *  第一个参数用于处理接受客户端连接，第二个参数用于处理请求内容
         */
        serverBootstrap.group(acceptor,worker);
        serverBootstrap.option(ChannelOption.SO_BACKLOG,1024);
        /**
         * 创建服务端channel
         */
        serverBootstrap.channel(NioServerSocketChannel.class);

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            /**
             * initChannel就是往责任链里添加处理方法来过滤接入的socketChannel
             * @param ch
             * @throws Exception
             */
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                /**
                 *  *  +---------------------------------------------------+---------------+
                 *  *  |                           ChannelPipeline         |               |
                 *  *  |                                                  \|/              |
                 *  *  |    +---------------------+            +-----------+----------+    |
                 *  *  |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
                 *  *  |    +----------+----------+            +-----------+----------+    |
                 *  *  |              /|\                                  |               |
                 *  *  |               |                                  \|/              |
                 *  *  |    +----------+----------+            +-----------+----------+    |
                 *  *  |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
                 *  *  |    +----------+----------+            +-----------+----------+    |
                 *  *  |              /|\                                  .               |
                 *  *  |               .                                   .               |
                 *  *  | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
                 *  *  |        [ method call]                       [method call]         |
                 *  *  |               .                                   .               |
                 *  *  |               .                                  \|/              |
                 *  *  |    +----------+----------+            +-----------+----------+    |
                 *  *  |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
                 *  *  |    +----------+----------+            +-----------+----------+    |
                 *  *  |              /|\                                  |               |
                 *  *  |               |                                  \|/              |
                 *  *  |    +----------+----------+            +-----------+----------+    |
                 *  *  |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
                 *  *  |    +----------+----------+            +-----------+----------+    |
                 *  *  |              /|\                                  |               |
                 *  *  +---------------+-----------------------------------+---------------+
                 *  *                  |                                  \|/
                 *  *  +---------------+-----------------------------------+---------------+
                 *  *  |               |                                   |               |
                 *  *  |       [ Socket.read() ]                    [ Socket.write() ]     |
                 *  *  |                                                                   |
                 *  *  |  Netty Internal I/O Threads (Transport Implementation)            |
                 *  *  +-------------------------------------------------------------------+
                 */
                ch.pipeline().addLast(new ServerHandler());
            }
        });

        try {
            /**
             * 绑定端口，并通过sync阻塞到绑定成功
             */
            ChannelFuture f = serverBootstrap.bind(port).sync();
            System.out.println("服务端启动成功，端口号为："+port);
            /**
             * 当前线程同步阻塞到socketChannel关闭
             */
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }
}
