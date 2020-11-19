package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;


/**
 * @Author:ggp
 * @Date:2020-09-06 14:40
 * @Description:
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
     * 两个while true 是为了实现goto
     */
    public static byte[] encrypt(byte[] plainText,ECPublicKey publicKey) throws Exception{
        while (true) {
            A1:
            while (true) {
                BigInteger k = new BigInteger(length, new Random());
                EllipticCurvePoint C = curve.scalar_multi(k, G);
                byte[] C1 = C.getBytes();
                EllipticCurvePoint S = curve.scalar_multi(h, publicKey.getPoint());
                if (null == S) {
                    throw new RuntimeException("the publicKey is infinite!");
                }
                EllipticCurvePoint share = curve.scalar_multi(k, publicKey.getPoint());
                byte[] t = KDFFunction.KDF(ByteUtil.bytesArray2bytes(share.getX().toByteArray(), share.getY().toByteArray()), plainText.length * 8);
                if (ByteUtil.isAllZero(t)) {
                    break A1;
                }
                byte[] C2 = new byte[plainText.length];
                for (int i = 0; i < plainText.length; i++) {
                    C2[i] = (byte) (plainText[i] ^ t[i]);
                }
                byte[] C3 = SM3Hash.hash(ByteUtil.bytesArray2bytes(share.getX().toByteArray(), plainText, share.getY().toByteArray()));
                return ByteUtil.bytesArray2bytes(C1, C3, C2);
            }
        }
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

    /**
     * 作为签名者的用户A具有长度为entlenA比特的可辨别的标识ID_A,记ENTL_A是由entlenA转换而成的两个字节，
     * 签名者和验证者都需要用密码杂凑函数来求的用户A的杂凑值ZA.按照GMT0003.1-2012 4.2.6和4.2.5给出的方法，
     * 讲椭圆曲线方程参数a、b、G的坐标G_x,G_y和P_A的坐标A_x,B_y的数据类型转换为比特串，
     * Z_A=H_256(ENTL_A||ID_A||a||b||G_x||G_Y||A_x||A_y||)
     * @param ID_A
     * @param A
     * @return
     */
    private static byte[] Z_A(byte[] ID_A,EllipticCurvePoint A) throws IOException {
        byte[] ENTL_A = Arrays.copyOfRange(ByteUtil.int2Bytes(ID_A.length),2,4);
        return SM3Hash.hash(ByteUtil.bytesArray2bytes(ENTL_A,ID_A,a.toByteArray(),b.toByteArray(),G_X.toByteArray(),G_Y.toByteArray(),A.getX().toByteArray(),A.getY().toByteArray()));
    }

    /**
     * 数字签名
     *
     * 设待签名的消息为M,为了获取消息M的数字签名(r,s)，作为签名者的用户A应该实现以下运算步骤；
     * A1:置M_=Z_A||M;
     * A2:计算e=Hv(M_),按照GMT0003.1-2012.4.2.4和4.2.3给出的方法将e的数据类型转换为整数；
     * A3:用随机数发生器产生随机数k属于[1,n-1];
     * A4:计算椭圆曲线上的点(x1,y1)=[k]G,按照GMT0003.1-2012的4.2.8给出的方法将x1的数据类型转换为整数；
     * A5:计算r=(e+x1) mod n,若r=0或r+k=n，则返回A3;
     * A6:计算s=((1+d_A)^-1*(k-r*d_A)) mod n,若s=0则返回A3;
     * A7:按GMT0003.1-2012 4.2.2 给出的细节将r、s的数据类型转换为字节串，消息M的签名为(r,s)。
     *
     * @return
     * 两个while true 实现goto语句
     */
    public static byte[] sign(byte[] src,byte[] ID_A,ECPrivateKey privateKey)throws IOException{
        byte[] M_ = ByteUtil.bytesArray2bytes(Z_A(ID_A,privateKey.getPoint()),src);
        BigInteger e = new BigInteger(1,SM3Hash.hash(M_));
        while (true) {
            A3:
            while (true) {
                BigInteger k = new BigInteger(length, new Random());
                EllipticCurvePoint P = curve.scalar_multi(k, G);
                BigInteger x1 = P.getX();
                BigInteger r = e.add(x1).mod(n);
                if (r.equals(BigInteger.valueOf(0L)) || r.add(k).equals(n)) {
                    break A3;
                }
                BigInteger s = (k.subtract(r.multiply(privateKey.getK())).multiply(curve.multiplyInverse(privateKey.getK().add(BigInteger.valueOf(1L)),n))).mod(n);
                if (s.equals(BigInteger.valueOf(0L))) {
                    break A3;
                }
                return new Signature(r, s).getEncode();
            }
        }
    }

    /**
     * 数字签名验证
     *
     * 为了检验收到的消息M'以及其数字签名(r',s'),作为验证者的用户B应实现以下运算步骤：
     * B1:检验r'属于[1,n-1]是否成立，若不成立则验证不通过；
     * B2:检验s'属于[1,n-1]是否成立，若不成立则验证不通过；
     * B3:置M'_=Z_A||M';
     * B4:计算e'=Hv(M'_),按照GMT0003.1-2012 4.2.4和4.2.3给出的方法将e'的数据类型转换为整数；
     * B5:按GMT0003.1-2012 4.2.3给出的方法将r'、s'的数据类型转换为整数，计算t=(r'+s')mod n,若t=0,则验证不通过；
     * B6:计算椭圆曲线点(x1',y1')=[s']G+[t]P_A;
     * B7:按照GMT0003.1-2012 4.2.8给出的方法将x1'的数据类型转换为整数，计算R=(e'+x1') mod n,校验R=r'是否成立，若成立则验证通过
     * 否则不通过。
     *
     * 原理
     * (x1',y1')=[s']G+[t]P_A
     *          =s'G+(s'+r')d_AG
     *          =(s'(1+d_A)+r'd_A)G
     *  根据签名算法A6可以知道s'=((1+d_A)^-1*(k-r*d_A)) mod n
     *  带入得(x1',y1')=kG,和签名的(x1,y1)是同一个点
     *
     * @param src
     * @param sign
     * @param ID_A
     * @param publicKey
     * @return
     */
    public static boolean verify(byte[] src,byte[] sign,byte[] ID_A,ECPublicKey publicKey)throws Exception{
        Signature signature = new Signature(sign);
        BigInteger r = signature.getR();
        BigInteger s = signature.getS();
        BigInteger temp = BigInteger.valueOf(1L);
        if(r.compareTo(n.subtract(temp))>0 || r.compareTo(temp)<0){
            return false;
        }
        if(s.compareTo(n.subtract(temp))>0 || s.compareTo(temp)<0){
            return false;
        }
        byte[] M_ = ByteUtil.bytesArray2bytes(Z_A(ID_A,publicKey.getPoint()),src);
        BigInteger e = new BigInteger(1,SM3Hash.hash(M_));
        BigInteger t = r.add(s);
        EllipticCurvePoint P = curve.pointAdd(curve.scalar_multi(s,G),curve.scalar_multi(t,publicKey.getPoint()));
        BigInteger R = e.add(P.getX()).mod(n);
        if(R.equals(r)){
            return true;
        }else{
            return false;
        }
    }
}
