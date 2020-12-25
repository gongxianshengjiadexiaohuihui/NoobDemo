package com.ggp.noob.demo.io.netty;

import com.ggp.noob.demo.io.Calculator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author:GGP
 * @Date:2020/3/25 20:58
 * @Description:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 用于处理读取客户端发送的消息
     * @param context
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg){
        ByteBuf readBuf = (ByteBuf)msg;
        byte[] bytes = new byte[readBuf.readableBytes()];
        readBuf.readBytes(bytes);
        String expression = new String(bytes);
        System.out.println("收到客户端的消息为："+expression);
        /**
         * 释放资源
         */
        readBuf.release();
        String calResult = String.valueOf(Calculator.addCal(expression));
        calResult = expression+"="+calResult;
        ByteBuf writeBuf = context.alloc().buffer(calResult.length());
        writeBuf.writeBytes(calResult.getBytes());
        context.write(writeBuf);
        context.flush();
    }


    /**
     * 本方法用作处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * 当出现异常就关闭连接
         */
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 信息获取完毕后操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
