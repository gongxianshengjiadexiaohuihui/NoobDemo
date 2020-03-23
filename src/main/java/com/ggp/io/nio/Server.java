package com.ggp.io.nio;


/**
 * @author: ggp
 * @Date: 2020/3/23 17:25
 * @Description:
 */
public class Server {
    /**
     * 默认端口
     */
    private static  int DEFAULT_PORT=12345;
    private static ServerHandler serverHandler;

    public static void start(){
        start(DEFAULT_PORT);
    }
    public static synchronized void start(int port){
        if(null != serverHandler){
            serverHandler.stop();
        }
        serverHandler = new ServerHandler(port);
        new Thread(serverHandler,"Server").start();
    }
}
