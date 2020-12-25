package com.ggp.noob.demo.io.aio;

/**
 * @Author:GGP
 * @Date:2020/3/25 19:20
 * @Description:
 */
public class Client {
    private static String DEFAULT_HOST="127.0.0.1";
    private static int DEFAULT_PORT=12345;
    private static AsyncClientHandler clientHandler;

    public static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }
    public static synchronized void start(String ip,int port){
        if(null != clientHandler){
            return;
        }
        clientHandler = new AsyncClientHandler(ip,port);
        new Thread(clientHandler,"Client").start();
    }
    public static void sendMsg(String msg){
        clientHandler.sendMsg(msg);
    }
}
