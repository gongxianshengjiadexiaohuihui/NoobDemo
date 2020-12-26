package com.ggp.noob.demo.algorithm.hash.consistent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author:ggp
 * @Date:2020/12/26 15:42
 * @Description:
 */
public class FNV1_32_HASHTest {

    @Test
    public void getHash() {
        System.out.println(FNV1_32_HASH.getHash("192.168.0.0:111"));
    }
}