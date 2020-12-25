package com.ggp.noob.demo.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author: ggp
 * @Date: 2020/3/23 13:53
 * @Description:
 */
public class Client {
    /**
     * 默认服务端端口
     */
    private static int DEFAULT_SERVER_PORT=12345;
    /**
     * 默认服务端ip
     */
    private static String DEFAULT_SERVER_IP="127.0.0.1";

    public static void send(String expression){
        System.out.println("客户端发送的消息为："+expression);
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(DEFAULT_SERVER_IP,DEFAULT_SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(expression);
            System.out.println("客户端收到的响应结果为："+in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
