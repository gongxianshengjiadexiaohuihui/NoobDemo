package com.ggp.noob.demo.io.nio;

/**
 * @Author:GGP
 * @Date:2020/3/23 21:57
 * @Description:
 */
public class Client {
    private static String DEFAULT_HOST="127.0.0.1";
    private static int DEFFAULT_PORT=12345;
    private static ClientHandle clientHandle;
    public static void start(){
        start(DEFAULT_HOST,DEFFAULT_PORT);
    }
    public static synchronized void start(String ip,int port){
        if(null != clientHandle){
            clientHandle.stop();
        }
        clientHandle= new ClientHandle(ip,port);
        new Thread(clientHandle,"Client").start();
    }
    public static void send(String msg) throws Exception{
        System.out.println("客户端发送的消息为："+msg);
        clientHandle.sendMsg(msg);
    }

}
