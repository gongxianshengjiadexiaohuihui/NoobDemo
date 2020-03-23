package com.ggp.io.bio;

import java.io.IOException;
import java.util.Random;

/**
 * @author: ggp
 * @Date: 2020/3/23 15:00
 * @Description:
 * 该模型最大的问题就是缺乏弹性伸缩能力，当客户端并发访问增加后，服务端线程个数和客户端并发访问数呈1：1的正比关系
 * Java中的线程也是比较宝贵的系统资源，线程数量快速膨胀后，系统的性能将急剧下降，随着访问量的继续增大，系统最终就死-掉-了
 * 如果使用线程池，能保证系统有限资源的控制
 * 但是，正因为限制了线程数量，如果发生大量并发请求，超过最大数量的线程就只能等待，直到线程池中的有空闲的线程可以被复用。而对Socket的输入流就行读取时，会一直阻塞，直到发生：
 *
 *     有数据可读
 *     可用数据以及读取完毕
 *     发生空指针或I/O异常
 *     所以在读取数据较慢时（比如数据量大、网络传输慢等），大量并发的情况下，其他接入的消息，只能一直等待，这就是最大的弊端。
 *
 */
public class Main {
    public static void main(String[] args) throws Exception{
        /**
         * 启动服务端
         */
        new Thread(()->{
            try {
                Server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        /**
         * 避免客户端在服务端启动前发送消息
         */
        Thread.sleep(100);
        Random random = new Random();
        new Thread(()->{
            while (true){
                int a = random.nextInt(100);
                int b = random.nextInt(100);
                Client.send(a+"+"+b);
            }
        }).start();
    }
}
