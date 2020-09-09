package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @Author:ggp
 * @Date:2020-09-09 14:40
 * @Description:
 */
public class Signature {
    private BigInteger r;
    private BigInteger s;

    public Signature(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }
    public Signature(byte[] bytes){
        int rLen = bytes[0];
        BigInteger r = new BigInteger(Arrays.copyOfRange(bytes,1,1+rLen));
        int sLen = bytes[1+rLen];
        if(rLen+sLen != bytes.length){
            throw new RuntimeException("the length is error!");
        }
        BigInteger s = new BigInteger(Arrays.copyOfRange(bytes,2+rLen,bytes.length));
        this.r = r;
        this.s = s;
    }
    public byte[] getEncode(){
        int rLen = r.bitLength()/8;
        int sLen = s.bitLength()/8;
        return ByteUtil.bytesArray2bytes(ByteUtil.int2Bytes(rLen),r.toByteArray(),ByteUtil.int2Bytes(sLen),s.toByteArray());
    }

    public BigInteger getR() {
        return r;
    }

    public void setR(BigInteger r) {
        this.r = r;
    }

    public BigInteger getS() {
        return s;
    }

    public void setS(BigInteger s) {
        this.s = s;
    }
}
