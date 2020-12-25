package com.ggp.noob.demo.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:GGP
 * @Date:2020/3/25 19:44
 * @Description:
 */
public class ReadHandler implements CompletionHandler<Integer,ByteBuffer> {
    private CountDownLatch latch;
    public ReadHandler(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        String calResult = new String(bytes);
        System.out.println("客户端收到的结果是："+calResult);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
        latch.countDown();
    }
}
