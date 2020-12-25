package com.ggp.noob.demo.algorithm.asymmetrical.ecc;


import com.ggp.noob.util.common.bytes.ByteUtil;

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
        int rLen = ByteUtil.bytes2Int(Arrays.copyOfRange(bytes,0,4));
        BigInteger r = new BigInteger(1,Arrays.copyOfRange(bytes,4,4+rLen));
        int sLen = ByteUtil.bytes2Int(Arrays.copyOfRange(bytes,4+rLen,8+rLen));
        if((rLen+sLen+8) != bytes.length){
            throw new RuntimeException("the length is error!");
        }
        BigInteger s = new BigInteger(1,Arrays.copyOfRange(bytes,8+rLen,bytes.length));
        this.r = r;
        this.s = s;
    }
    public byte[] getEncode(){
        byte[] rBytes = ByteUtil.toUnsigned(r);
        int rLen = rBytes.length;
        byte[] sBytes = ByteUtil.toUnsigned(s);
        int sLen = sBytes.length;
        return ByteUtil.bytesArray2bytes(ByteUtil.int2Bytes(rLen),rBytes,ByteUtil.int2Bytes(sLen),sBytes);
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
