package com.ggp.noob.demo.algorithm.hash.consistent;


import org.junit.Test;

/**
 * @Author:ggp
 * @Date:2020/12/25 16:38
 * @Description:
 */
public class ConsistentHashingWithoutVirtualNodeTest {
    @Test
    public void test(){
        ConsistentHashingWithoutVirtualNode hashing = new ConsistentHashingWithoutVirtualNode();
        String[] servers = new String[]{"11.12.86.49:8080","11.12.86.50:8080","11.12.86.51:8080","11.12.86.52:8080","11.12.86.53:8080"};
        hashing.init(servers);
        for (int i = 0; i <20 ; i++) {
            hashing.putServer("test"+i,i);
        }
        hashing.print();
    }
    @Test
    public void test_add_server(){
        ConsistentHashingWithoutVirtualNode hashing = new ConsistentHashingWithoutVirtualNode();
        String[] servers = new String[]{"11.12.86.49:8080","11.12.86.50:8080","11.12.86.51:8080","11.12.86.52:8080","11.12.86.53:8080","11.12.86.54:8080"};
        hashing.init(servers);
        for (int i = 0; i <20 ; i++) {
            hashing.putServer("test"+i,i);
        }
        hashing.print();
    }
    @Test
    public void test_del_server(){
        ConsistentHashingWithoutVirtualNode hashing = new ConsistentHashingWithoutVirtualNode();
        String[] servers = new String[]{"11.12.86.49:8080","11.12.86.50:8080","11.12.86.52:8080","11.12.86.53:8080"};
        hashing.init(servers);
        for (int i = 0; i <20 ; i++) {
            hashing.putServer("test"+i,i);
        }
        hashing.print();
    }
}