package com.ggp.juc.juc05_varhandler;


import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @Author:GGP
 * @Date:2020/4/8 21:26
 * @Description:
 */
public class V01_HelloVarHandler {
    public static void main(String[] args) {
        Demo1 t = new Demo1();
        System.out.println(t.handle.get(t));
        t.handle.set(t,9);
        System.out.println(t.x);
        t.handle.compareAndSet(t,9,10);
        System.out.println(t.x);
        t.handle.getAndAdd(t,10);
        System.out.println(t.x);
    }
}
class Demo1{
    int x=8;
    public VarHandle handle;
     {
        try {
            handle= MethodHandles.lookup().findVarHandle(Demo1.class,"x",int.class);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
