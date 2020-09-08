package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;


/**
 * SM2非压缩公钥格式字节串长度为65字节，压缩格式长度为33字节，若公钥y坐标最后一位为0，则首字节为0x02，否则为0x03。非压缩格式公钥首字节为0x04。
 */
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
     * @param k
     * @return
     */
    public static ECKeyPair generateKeyPair(BigInteger k){
        return curve.generateKeyPair(k);
    }
    /**
     * 生成密钥对
     * @param random
     * @return
     */
    public static ECKeyPair generateKeyPair(Random random){
        return curve.generateKeyPair(new BigInteger(length,random));
    }

    /**
     * sm2加密
     * 设需要发送的消息比特串M，klen为M的比特长度。
     * 为了对明文M进行加密，作为加密者的用户A应该实现以下运算步骤：
     * A1:用随机数发生器产生随机数k属于[1,n-1];
     * A2:计算椭圆曲线点C1=[k]G=(x1,y1),按GMT 0003.1-2012的4.2.9和4.2.5给出的方法,将C1的数据类型转换为比特串；
     * A3:计算S=[h]P_B,若S是无穷远点，则报错并退出；
     * A4:计算椭圆曲线点[k]P_B=(x2,y2),按GMT 0003.1-2012的4.2.6和4.2.5给出的方法，将坐标x2,y2的数据类型转为比特串；
     * A5:计算t=KDF(x2||y2,klen),若t为全0比特串，则返回A1；
     * A6:计算C2=M^t;
     * A7:计算C3=hash(x2||M||y2)
     * A8:输出密文C=C1||C3||C2
     * sm2 C1是65个字节 C3是32个字节
     * @param plainText
     * @return
     */
    public static byte[] encrypt(byte[] plainText,ECPublicKey publicKey) throws Exception{
        BigInteger k = new BigInteger(length,new Random());
        EllipticCurvePoint C = curve.scalar_multi(k,G);
        byte[] C1 = C.getBytes();
        EllipticCurvePoint S = curve.scalar_multi(h,publicKey.getPoint());
        if(null == S){
            throw new RuntimeException("the publicKey is infinite!");
        }
        EllipticCurvePoint share = curve.scalar_multi(k,publicKey.getPoint());
        byte[] t = KDFFunction.KDF(ByteUtil.bytesArray2bytes(share.getX().toByteArray(),share.getY().toByteArray()),plainText.length*8);
        if(ByteUtil.isAllZero(t)){
            return k.toByteArray();
        }
        byte[] C2 = new byte[plainText.length];
        for (int i = 0; i <plainText.length ; i++) {
            C2[i] = (byte)(plainText[i]^t[i]);
        }
        byte[] C3 = SM3Hash.hash(ByteUtil.bytesArray2bytes(share.getX().toByteArray(),plainText,share.getY().toByteArray()));
        return ByteUtil.bytesArray2bytes(C1,C3,C2);
    }

    /**
     * 解密
     * sm2 C1是65个字节 C3是32个字节
     * 设klen为密文中C2的比特长度。
     * 为了对密文C=C1||C3||C2进行解密，作为解密者的用户B应实现以下运算步骤:
     * B1:从C中取出比特串C1，将C1的数据类型转换为椭圆曲线上的点，验证C1是否满足椭圆曲线方程，若不满足报错退出。
     * B2:计算椭圆曲线点S=[h]C1,若S是无穷远点，则报错退出;
     * B3:计算[d_B]c1=(x2,y2),按GMT0003.1-2012的4.2.6和4.2.5给出的方法，将坐标x2,y2的数据类型转换为比特串。
     * B4:计算t=KDF(x2||y2,klen)
     * B5:从C中取出比特串C2,计算M'=C2^t;
     * B6:计算u=Hash(x2||M'||y2),从C中取出比特串C3,若u=/C3,则报错退出；
     * B7:输出明文M'
     * @param cipherText
     * @param privateKey
     * @return
     */
    public static byte[] decrypt(byte[] cipherText,ECPrivateKey privateKey)throws Exception{
        int klen = cipherText.length - 65-32;
        byte[] C1 = Arrays.copyOfRange(cipherText,0,65);
        byte[] C3 = Arrays.copyOfRange(cipherText,65,97);
        byte[] C2 = Arrays.copyOfRange(cipherText,97,cipherText.length);
        EllipticCurvePoint C_1 = new EllipticCurvePoint(C1);
        EllipticCurvePoint S = curve.scalar_multi(h,C_1);
        if(null == S){
            throw new RuntimeException("the s is infinite!");
        }
        EllipticCurvePoint share = curve.scalar_multi(privateKey.getK(),C_1);
        byte[] t = KDFFunction.KDF(ByteUtil.bytesArray2bytes(share.getX().toByteArray(),share.getY().toByteArray()),klen);
        byte[] plainText = new byte[klen];
        for (int i = 0; i <klen ; i++) {
            plainText[i] = (byte)(C2[i]^t[i]);
        }
        byte[] u  = SM3Hash.hash(ByteUtil.bytesArray2bytes(share.getX().toByteArray(),plainText,share.getY().toByteArray()));
        if(!Arrays.equals(C3,u)){
            throw new RuntimeException("hash is not consistent!");
        }
        return plainText;
    }
}
