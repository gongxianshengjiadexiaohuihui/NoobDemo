package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

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
}