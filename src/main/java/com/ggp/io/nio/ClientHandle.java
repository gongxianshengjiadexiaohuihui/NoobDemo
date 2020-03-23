package com.ggp.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author:GGP
 * @Date:2020/3/23 21:58
 * @Description:
 */
public class ClientHandle implements Runnable{
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean started;
    public ClientHandle(String ip, int port) {
        this.host =ip;
        this.port =port;

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            doConnection();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (started){
            try {
                selector.select(1000);
                selector.select();
                Set<SelectionKey> keys=selector.selectedKeys();
                Iterator<SelectionKey>iterator = keys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException{
        if(key.isValid()){
            SocketChannel sc = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()){
                    /**
                     * 结束建立连接请求
                     */
                }else {
                    System.exit(1);
                }
            }
            if(key.isReadable()){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(buffer);
                if(readBytes>0){
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String result =  new String(bytes,"UTF-8");
                    System.out.println("客户端收到的消息："+result);
                }else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    public void stop() {
        started =false;
    }

    public void sendMsg(String msg) throws Exception {
        socketChannel.register(selector,SelectionKey.OP_READ);
        doWrite(socketChannel,msg);
    }

    public void doConnection() throws IOException{
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            /**
             * 如果返回true，则表示通道已经被立刻建立，在非阻塞情况下返回false后续需要调用finishConnect方法
             * 完成通道建立
             */
            return;
        }else {
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }
    private void doWrite(SocketChannel socketChannel, String msg)throws Exception{
        /**
         * 防止上一个没有写完，另一个又开始写了
         */
        Thread.sleep(1);
        byte[] bytes = msg.getBytes();
        ByteBuffer writerBuffer = ByteBuffer.allocate(bytes.length);
        writerBuffer.put(bytes);
        writerBuffer.flip();
        socketChannel.write(writerBuffer);
    }

}
