package com.ggp.noob.demo.concurrent.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author:GGP
 * @Date:2020/4/27 20:20
 * @Description:
 */
public class T10_ParallelStreamAPI {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i <10000 ; i++) {
            nums.add(100000+random.nextInt(1000000));
        }
        long start = System.currentTimeMillis();
        nums.forEach(v->isPrime(v));
        long end = System.currentTimeMillis();
        System.out.println(end-start);


        start = System.currentTimeMillis();
        nums.parallelStream().forEach(T10_ParallelStreamAPI::isPrime);
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }
    static boolean isPrime(int num){
        for(int i = 2; i< num/2;i++){
            if(num%i==0){
                return false;
            }
        }
        return true;
    }
}
