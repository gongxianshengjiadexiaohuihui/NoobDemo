package com.ggp.noob.demo.delayqueue.test;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Created by gongguanpeng on 2022/3/1 10:14
 */
@Component
public class Worker implements Serializable {

    public void workVoid(){
        System.out.println("开始处理工作 void");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:");
    }
    public void workBoolean(boolean param){
        System.out.println("开始处理工作 Z");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workByte(byte param){
        System.out.println("开始处理工作 B");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workChar(char param){
        System.out.println("开始处理工作 C");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workShort(short param){
        System.out.println("开始处理工作 S");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workInt(int param){
        System.out.println("开始处理工作 I");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workLong(long param){
        System.out.println("开始处理工作 L");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workFloat(int param){
        System.out.println("开始处理工作 F");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workDouble(double param){
        System.out.println("开始处理工作 D");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workArray(int[] param){
        System.out.println("开始处理工作 [");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }
    public void workObject(Person param){
        System.out.println("开始处理工作 L");
        System.out.println("work time:"+ LocalDateTime.now()+"Received:"+param);
    }

}
