package com.ggp.asymmetrical.ecc;


import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

public class SM3HashTest {
    @Test
    public void test_padding() throws Exception{
        BigInteger m = new BigInteger("011000010110001001100011",2);
        SM3Hash sm3Hash = new SM3Hash();
        byte[] result = sm3Hash.padding(m.toByteArray());
        System.out.println(Arrays.toString(m.toByteArray()));
        System.out.println(Arrays.toString(result));
        byte s = 0x11;
    }
}