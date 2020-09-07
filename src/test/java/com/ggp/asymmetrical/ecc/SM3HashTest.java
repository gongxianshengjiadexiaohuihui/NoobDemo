package com.ggp.asymmetrical.ecc;


import com.ggp.util.ByteUtil;
import org.junit.Test;

public class SM3HashTest {
    @Test
    public void test_padding() throws Exception{
        SM3Hash sm3Hash = new SM3Hash();
        byte[] result = sm3Hash.padding("abc".getBytes());
        ByteUtil.print(result,16);
    }
    @Test
    public void test_extend() throws Exception{
        SM3Hash sm3Hash = new SM3Hash();
        byte[] result = sm3Hash.padding("abc".getBytes());
        int[] W = new int[68];
        int[] W_= new int[64];
        sm3Hash.messageExtend(W,W_,result);
        ByteUtil.print(ByteUtil.intArray2bytes(W),16);
        System.out.println("\n----------------");
        ByteUtil.print(ByteUtil.intArray2bytes(W_),16);
    }
    @Test
    public void test_compression() throws Exception{
        SM3Hash sm3Hash = new SM3Hash();
        byte[] result = sm3Hash.padding("abc".getBytes());
        byte[] hash = sm3Hash.iterativeCompression(result);
        ByteUtil.print(hash,16);
    }

}