package com.ggp.noob.demo.io.aio;

/**
 * @Author:GGP
 * @Date:2020/3/24 20:13
 * @Description:
 */
public class Server {
    private static int  DEFAULT_PORT=12345;
    private static AsyncServerHandle serverHandle;
    public volatile static long clientCount=0;
    public static void start(){
        start(DEFAULT_PORT);
    }
    public static synchronized void start(int port){
        if(null != serverHandle ){
            return;
        }
        serverHandle = new AsyncServerHandle(port);
        new Thread(serverHandle,"Server").start();
    }
}
