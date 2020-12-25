package com.ggp.noob.demo.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:GGP
 * @Date:2020/3/25 19:38
 * @Description:
 */
public class WriterHandler implements CompletionHandler<Integer,ByteBuffer> {
    private AsynchronousSocketChannel socketChannel;
    private CountDownLatch latch;
    public WriterHandler(AsynchronousSocketChannel socketChannel,CountDownLatch latch) {
        this.socketChannel = socketChannel;
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
        if(byteBuffer.hasRemaining()){
            /**
             * 完成全部数据读写
             */
            socketChannel.write(byteBuffer,byteBuffer,this);
        }else{
            /**
             * 读取数据
             */
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer,buffer,new ReadHandler(latch));

        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        latch.countDown();
    }
}
