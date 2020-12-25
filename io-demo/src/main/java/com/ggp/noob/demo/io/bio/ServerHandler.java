package com.ggp.noob.demo.io.bio;

import com.ggp.noob.demo.io.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author: ggp
 * @Date: 2020/3/23 14:09
 * @Description:
 */
public class ServerHandler implements Runnable{
    private Socket socket;

    public ServerHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            /**
             * 接收客户端发过来的消息
             */
            String expression;
            /**
             * 计算结果返回给客户端
             */
            String result;
            while (true){
                if(null == (expression = in.readLine())){
                    break;
                }else{
                    System.out.println("服务器收到消息："+expression);
                }
                result = String.valueOf(Calculator.addCal(expression));
                out.println(result);
            }
        }catch (Exception e){
            if(null != in){
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
