package com.ggp.noob.demo.io.nio;

import com.ggp.noob.demo.io.Calculator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

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
        try {
            /**
             * 创建选择器
             */
            selector = Selector.open();
            /**
             * 打开监听通道
             */
            serverSocketChannel = ServerSocketChannel.open();
            /**
             * 开启非阻塞模式
             */
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            /**
             * 监听客户端连接请求 ,注册selector这个管家，由selector来管理这个通道
             */
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            /**
             * 标记服务已经开启
             */
            started = true;
            System.out.println("服务器已启动，端口号："+port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        started = false;
    }

    @Override
    public void run() {
        while (started) {
            try {
                selector.select(1000);
                /**
                 * 阻塞,只有当至少一个注册的事件发生的时候才会继续.
                 */
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator =  keys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if(null!= key){
                            key.cancel();
                            if(null !=  key.channel()){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(null!= selector){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key)throws IOException {
        if(key.isValid()){
            /**
             * 处理新接入的请求信息
             */
            if(key.isAcceptable()){
                /**
                 * 只有serverSocketChannel才能收到接入请求
                 */
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                /**
                 * 监听到接入的请求，获取ServerSockChannel实例，并创建SocketChannel实例，
                 * 完成此操作，意味着完成TCP的三次握手，TCP物理链路正式建立
                 */
                SocketChannel sc = ssc.accept();
                /**
                 * 设置非阻塞，监听来自客户端的读请求
                 */
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);
            }
            if(key.isReadable()){
                SocketChannel socketChannel=(SocketChannel)key.channel();
                /**
                 * 创建ByteBuffer，并开辟一个1M的缓冲区
                 */
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                /**
                 * 读取请求流，返回读取的字节数
                 */
                int readBytes = socketChannel.read(buffer);
                if(readBytes>0){
                    /**
                     * 这个ByteBuffer只有一个指针position用来读写，调用flip（）方法后
                     * limit = position  限制最多读到的字节位置。此时position的值是写入最后一个字节的位置
                     * 然后position = 0  让指针归0，从第一个字节开始读
                     */
                    buffer.flip();
                    /**
                     * buffer.remaining()返回的是 limit-position 也就是可读字节数
                     */
                    byte[] bytes = new byte[buffer.remaining()];
                    /**
                     * 讲缓冲区 字节读到字节数组中
                     */
                    buffer.get(bytes);
                    String expression = new String(bytes,"UTF-8");
                    System.out.println("服务端收到的消息为："+expression);
                    String result= null;
                    result =expression+"="+String.valueOf( Calculator.addCal(expression));

                    doWrite(socketChannel,result);

                }else if(readBytes < 0){
                    /**
                     * 链路已经关闭，释放资源
                     */
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String result)throws IOException {
        byte[] bytes = result.getBytes();
        ByteBuffer writerBuffer = ByteBuffer.allocate(bytes.length);
        writerBuffer.put(bytes);
        writerBuffer.flip();
        socketChannel.write(writerBuffer);
    }
}
