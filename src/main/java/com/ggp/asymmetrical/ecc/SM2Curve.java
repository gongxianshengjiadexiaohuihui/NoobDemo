package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;
import java.util.Random;

public class SM2Curve {
    /**
     * 密钥长度
     */
    public static final int length = 256;

    /**
     * 参数定义
     */
    public static final BigInteger a = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",16);
    public static final BigInteger b = new BigInteger("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",16);
    public static final BigInteger p = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",16);
    public static final BigInteger n = new BigInteger("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",16);
    public static final BigInteger h = BigInteger.valueOf(1L);
    public static final BigInteger G_X = new BigInteger("32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",16);
    public static final BigInteger G_Y = new BigInteger("BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0",16);
    public static final EllipticCurvePoint G = new EllipticCurvePoint(G_X,G_Y);
    public static final FpEllipticCurve curve = new FpEllipticCurve(a,b,p,n,h,G);

    /**
     * 生成密钥对
     * @return
     */
    public static ECKeyPair generateKeyPair(){
        return curve.generateKeyPair(new BigInteger(length,new Random()));
    }

    /**
     * 生成密钥对
     * @param random
     * @return
     */
    public static ECKeyPair generateKeyPair(Random random){
        return curve.generateKeyPair(new BigInteger(length,random));
    }
}
