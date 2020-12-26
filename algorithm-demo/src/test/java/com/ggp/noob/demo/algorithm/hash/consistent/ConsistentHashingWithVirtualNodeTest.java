package com.ggp.noob.demo.algorithm.hash.consistent;

import org.junit.Test;


/**
 * @Author:ggp
 * @Date:2020/12/26 18:50
 * @Description:
 */
public class ConsistentHashingWithVirtualNodeTest {
    @Test
    public void test(){
        ConsistentHashingWithVirtualNode hashing = new ConsistentHashingWithVirtualNode();
        String[] servers = new String[]{"11.12.86.49:8080","11.12.86.50:8080","11.12.86.51:8080","11.12.86.52:8080","11.12.86.53:8080"};
        hashing.init(servers);
        for (int i = 0; i <20 ; i++) {
            hashing.putServer("test"+i,i);
        }
        hashing.print();
    }
    @Test
    public void test_add_server(){
        ConsistentHashingWithVirtualNode hashing = new ConsistentHashingWithVirtualNode();
        String[] servers = new String[]{"11.12.86.49:8080","11.12.86.50:8080","11.12.86.51:8080","11.12.86.52:8080","11.12.86.53:8080","11.12.86.54:8080"};
        hashing.init(servers);
        for (int i = 0; i <20 ; i++) {
            hashing.putServer("test"+i,i);
        }
        hashing.print();
    }
    @Test
    public void test_del_server(){
        ConsistentHashingWithVirtualNode hashing = new ConsistentHashingWithVirtualNode();
        String[] servers = new String[]{"11.12.86.49:8080","11.12.86.50:8080","11.12.86.52:8080","11.12.86.53:8080"};
        hashing.init(servers);
        for (int i = 0; i <20 ; i++) {
            hashing.putServer("test"+i,i);
        }
        hashing.print();
    }
}