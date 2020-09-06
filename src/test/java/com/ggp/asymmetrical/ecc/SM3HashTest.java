package com.ggp.asymmetrical.ecc;


import com.ggp.util.ByteUtil;
import org.junit.Test;

import java.math.BigInteger;

public class SM3HashTest {
    @Test
    public void test_padding() throws Exception{
        BigInteger m = new BigInteger("011000010110001001100011",2);
        SM3Hash sm3Hash = new SM3Hash();
        byte[] result = sm3Hash.padding(m.toByteArray());
        ByteUtil.printBits(m.toByteArray());
        System.out.println("result");
        ByteUtil.printBits(result);
    }
    @Test
    public void test(){
        byte[] bytes = new byte[1];
        bytes[0] =(byte)0x11;
        ByteUtil.printBits(bytes);
    }
}