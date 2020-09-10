package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-10 15:42
 * @Description:
 */
public class SM4CryptographyTest {

    @Test
    public void test_τ() {
        int A = 0X10203233;
        int B = 0X2B9C1CA9;
        Assert.assertEquals(B,SM4Cryptography.τ(A));
    }

    @Test
    public void test_generateRoundKey() {
        byte[] key = new BigInteger("0123456789ABCDEFFEDCBA9876543210",16).toByteArray();
        int[] roundkey = SM4Cryptography.generateRoundKey(key);
        for (int i = 0; i <roundkey.length ; i++) {
            ByteUtil.print(ByteUtil.int2Bytes(roundkey[i]),16);
        }
    }
    @Test
    public void test_encrypt(){
        byte[] key = new BigInteger("0123456789ABCDEFFEDCBA9876543210",16).toByteArray();
        byte[] src = new BigInteger("0123456789ABCDEFFEDCBA9876543210",16).toByteArray();
        byte[] cipher = SM4Cryptography.encryptOrDecrypt(src,key,true);
        byte[] plain = SM4Cryptography.encryptOrDecrypt(cipher,key,false);
        ByteUtil.print(cipher,16);
        ByteUtil.print(plain,16);
    }
}