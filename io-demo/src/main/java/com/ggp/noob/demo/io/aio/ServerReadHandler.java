package com.ggp.noob.demo.io.aio;

import com.ggp.noob.demo.io.Calculator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author:GGP
 * @Date:2020/3/24 20:30
 * @Description:
 */
public class ServerReadHandler implements CompletionHandler<Integer,ByteBuffer> {
    private AsynchronousSocketChannel channel;
    public ServerReadHandler(AsynchronousSocketChannel channel) {
        this.channel =  channel;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[]  message = new byte[attachment.remaining()];
        attachment.get(message);
        String expression = new String(message);
        System.out.println("服务端收到的消息为："+expression);
        String calResult = String.valueOf(Calculator.addCal(expression));
        doWrite(expression+"="+calResult);
    }

    private void doWrite(String s) {
        byte[] bytes= s.getBytes();
        ByteBuffer writeBuffer= ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(writeBuffer.hasRemaining()){
                    channel.write(writeBuffer,writeBuffer,this);
                }else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    channel.read(readBuffer,readBuffer,new ServerReadHandler(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
