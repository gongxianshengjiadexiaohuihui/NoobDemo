package com.ggp.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: ggp
 * @Date: 2020/3/23 13:56
 * @Description:
 */
public class Server {
    /**
     * 默认端口号
     */
    private static int DEFAULT_PORT=12345;
    /**
     * 单例的ServerSocket
     */
    private static ServerSocket server;

    public static void start() throws IOException{
        start(DEFAULT_PORT);
    }

    /**
     * 加synchronized的原因是为了单例，如果多个方法同时访问，可能导致server被初始化多次
     * @param port
     * @throws IOException
     */
    public synchronized  static void start(int port) throws IOException{
        if(null != server){
            return;
        }

        server = new ServerSocket(port);
        System.out.println("服务器已经启动，端口号："+port);
        while (true){
            /**
             * 如果没有客户端接入，就会一直阻塞在这里
             * 源码注释：
             * Listens for a connection to be made to this socket and accepts
             * it. The method blocks until a connection is made.
             *
             */
            Socket socket = server.accept();

            new Thread(new ServerHandler(socket)).start();
        }
    }

}
