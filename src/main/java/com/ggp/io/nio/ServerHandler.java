package com.ggp.io.nio;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author: ggp
 * @Date: 2020/3/23 17:26
 * @Description:
 */
public class ServerHandler implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean started;
    public ServerHandler(int port) {

    }

    public void stop() {
    }


    @Override
    public void run() {

    }
}
