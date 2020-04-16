package com.ggp.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author:GGP
 * @Date:2020/4/16 23:14
 * @Description:
 * 虚引用在声明的时候必须有一个队列，比如下面的例子，当Demo这个对象被回收了，phantomReference这个引用就会被放在QUEUE这个队列中。
 * 使用场景是管理对外内存，因为对外内存可能是jvm堆里一个很小的对象关联堆外一大片内存，而且垃圾回收没法回收堆外内存，这时候写jvm的人或者写netty的人就可以检测这个队列如果检测到了phantomReference这个引用
 * 就把它关联的堆外内存用特殊的底层方法回收。
 */
public class R04_PhantomRef {
    private static final List<Object> LIST = new LinkedList<>();
    private static final ReferenceQueue<Demo> QUEUE = new ReferenceQueue<>();
    public static void main(String[] args) {
        PhantomReference<Demo> phantomReference = new PhantomReference<>(new Demo(),QUEUE);
        new Thread(()->{
            while (true){
                LIST.add(new byte[1024*1024]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get());
            }
        }
        ).start();
        new Thread(()->{
            while(true){
                Reference<?extends Demo> poll = QUEUE.poll();
                if(null != poll){
                    System.out.println("虚引用对象被jvm回收");
                }
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

