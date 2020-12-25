package com.ggp.noob.demo.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Scanner;

/**
 * @Author:GGP
 * @Date:2020/3/25 21:45
 * @Description:
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg)throws Exception{
        ByteBuf readBuf = (ByteBuf)msg;
        byte[] bytes = new byte[readBuf.readableBytes()];
        readBuf.readBytes(bytes);
        System.out.println("客户端收到的结果是："+new String(bytes));
        readBuf.release();
    }
    /**
     * 本方法用于处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }



    /**
     * 本方法用于向服务端发送信息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        ByteBuf writeBuf = ctx.alloc().buffer(expression.length());
        writeBuf.writeBytes(expression.getBytes());
        ctx.write(writeBuf);
        ctx.flush();
    }

}
