package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;

import java.util.Arrays;

/**
 * @Author:ggp
 * @Date:2020-09-10 11:13
 * @Description:
 * SM4密码算法是一个分组算法。该算法的分组长度为128比特，秘钥长度为128比特。加密算法与秘钥扩展算法
 * 都采用32轮非线性迭代结构。数据解密和数据加密的算法结构相同，只是轮秘钥的使用顺序相反，解密轮秘钥
 * 是加密轮秘钥的逆序。
 */
public class SM4Cryptography {
    /**
     * 固定参数
     * 取值方法
     * 设ck(i,j)为CK(i)的第j字节(i=0,1,...,31;j=0,1,2,3)，
     * 即CK(i)=(ck(i,0),ck(i,1),ck(i,2),ck(i,3))
     * 则ck(i,j)=(4*i+j)*7(mod 256)
     */
    private static int[] CK = { 0X00070E15, 0X1C232A31, 0X383F464D, 0X545B6269,
                                0X70777E85, 0X8C939AA1, 0XA8AFB6BD, 0XC4CBD2D9,
                                0XE0E7EEF5, 0XFC030A11, 0X181F262D, 0X343B4249,
                                0X50575E65, 0X6C737A81, 0X888F969D, 0XA4ABB2B9,
                                0XC0C7CED5, 0XDCE3EAF1, 0XF8FF060D, 0X141B2229,
                                0X30373E45, 0X4C535A61, 0X686F767D, 0X848B9299,
                                0XA0A7AEB5, 0XBCC3CAD1, 0XD8DFE6ED, 0XF4FB0209,
                                0X10171E25, 0X2C333A41, 0X484F565D, 0X646B7279 };
    /**
     * 系统参数
     */
    private static int[] FK=new int[]{
            0xA3B1BAC6,0x56AA3350,0x677D9197,0xB27022DC
    };
    /**
     * S盒 用于线性变换
     */
    private static byte[][] Sbox = {
            {(byte)0XD6, (byte)0X90, (byte)0XE9, (byte)0XFE, (byte)0XCC, (byte)0XE1, (byte)0X3D, (byte)0XB7, (byte)0X16, (byte)0XB6, (byte)0X14, (byte)0XC2, (byte)0X28, (byte)0XFB, (byte)0X2C, (byte)0X05},
            {(byte)0X2B, (byte)0X67, (byte)0X9A, (byte)0X76, (byte)0X2A, (byte)0XBE, (byte)0X04, (byte)0XC3, (byte)0XAA, (byte)0X44, (byte)0X13, (byte)0X26, (byte)0X49, (byte)0X86, (byte)0X06, (byte)0X99},
            {(byte)0X9C, (byte)0X42, (byte)0X50, (byte)0XF4, (byte)0X91, (byte)0XEF, (byte)0X98, (byte)0X7A, (byte)0X33, (byte)0X54, (byte)0X0B, (byte)0X43, (byte)0XED, (byte)0XCF, (byte)0XAC, (byte)0X62},
            {(byte)0XE4, (byte)0XB3, (byte)0X1C, (byte)0XA9, (byte)0XC9, (byte)0X08, (byte)0XE8, (byte)0X95, (byte)0X80, (byte)0XDF, (byte)0X94, (byte)0XFA, (byte)0X75, (byte)0X8F, (byte)0X3F, (byte)0XA6},
            {(byte)0X47, (byte)0X07, (byte)0XA7, (byte)0XFC, (byte)0XF3, (byte)0X73, (byte)0X17, (byte)0XBA, (byte)0X83, (byte)0X59, (byte)0X3C, (byte)0X19, (byte)0XE6, (byte)0X85, (byte)0X4F, (byte)0XA8},
            {(byte)0X68, (byte)0X6B, (byte)0X81, (byte)0XB2, (byte)0X71, (byte)0X64, (byte)0XDA, (byte)0X8B, (byte)0XF8, (byte)0XEB, (byte)0X0F, (byte)0X4B, (byte)0X70, (byte)0X56, (byte)0X9D, (byte)0X35},
            {(byte)0X1E, (byte)0X24, (byte)0X0E, (byte)0X5E, (byte)0X63, (byte)0X58, (byte)0XD1, (byte)0XA2, (byte)0X25, (byte)0X22, (byte)0X7C, (byte)0X3B, (byte)0X01, (byte)0X21, (byte)0X78, (byte)0X87},
            {(byte)0XD4, (byte)0X00, (byte)0X46, (byte)0X57, (byte)0X9F, (byte)0XD3, (byte)0X27, (byte)0X52, (byte)0X4C, (byte)0X36, (byte)0X02, (byte)0XE7, (byte)0XA0, (byte)0XC4, (byte)0XC8, (byte)0X9E},
            {(byte)0XEA, (byte)0XBF, (byte)0X8A, (byte)0XD2, (byte)0X40, (byte)0XC7, (byte)0X38, (byte)0XB5, (byte)0XA3, (byte)0XF7, (byte)0XF2, (byte)0XCE, (byte)0XF9, (byte)0X61, (byte)0X15, (byte)0XA1},
            {(byte)0XE0, (byte)0XAE, (byte)0X5D, (byte)0XA4, (byte)0X9B, (byte)0X34, (byte)0X1A, (byte)0X55, (byte)0XAD, (byte)0X93, (byte)0X32, (byte)0X30, (byte)0XF5, (byte)0X8C, (byte)0XB1, (byte)0XE3},
            {(byte)0X1D, (byte)0XF6, (byte)0XE2, (byte)0X2E, (byte)0X82, (byte)0X66, (byte)0XCA, (byte)0X60, (byte)0XC0, (byte)0X29, (byte)0X23, (byte)0XAB, (byte)0X0D, (byte)0X53, (byte)0X4E, (byte)0X6F},
            {(byte)0XD5, (byte)0XDB, (byte)0X37, (byte)0X45, (byte)0XDE, (byte)0XFD, (byte)0X8E, (byte)0X2F, (byte)0X03, (byte)0XFF, (byte)0X6A, (byte)0X72, (byte)0X6D, (byte)0X6C, (byte)0X5B, (byte)0X51},
            {(byte)0X8D, (byte)0X1B, (byte)0XAF, (byte)0X92, (byte)0XBB, (byte)0XDD, (byte)0XBC, (byte)0X7F, (byte)0X11, (byte)0XD9, (byte)0X5C, (byte)0X41, (byte)0X1F, (byte)0X10, (byte)0X5A, (byte)0XD8},
            {(byte)0X0A, (byte)0XC1, (byte)0X31, (byte)0X88, (byte)0XA5, (byte)0XCD, (byte)0X7B, (byte)0XBD, (byte)0X2D, (byte)0X74, (byte)0XD0, (byte)0X12, (byte)0XB8, (byte)0XE5, (byte)0XB4, (byte)0XB0},
            {(byte)0X89, (byte)0X69, (byte)0X97, (byte)0X4A, (byte)0X0C, (byte)0X96, (byte)0X77, (byte)0X7E, (byte)0X65, (byte)0XB9, (byte)0XF1, (byte)0X09, (byte)0XC5, (byte)0X6E, (byte)0XC6, (byte)0X84},
            {(byte)0X18, (byte)0XF0, (byte)0X7D, (byte)0XEC, (byte)0X3A, (byte)0XDC, (byte)0X4D, (byte)0X20, (byte)0X79, (byte)0XEE, (byte)0X5F, (byte)0X3E, (byte)0XD7, (byte)0XCB, (byte)0X39, (byte)0X48}
    };

    /**
     * 非线性变换τ
     * τ由4个并行的S盒构成。
     * 设输入为A=(a(0),a(1),a(2),a(3)),输出为B=(b(0),b(1),b(2),b(3))
     * (b(0),b(1),b(2),b(3))=(Sbox(a(0)),Sbox(a(1)),Sbox(a(2)),Sbox(a(3)))
     * @param A
     * @return
     */
    public static int τ(int A){
        byte[] a = ByteUtil.int2Bytes(A);
        byte[] b = new byte[4];
        int m,n;
        for (int i = 0; i <4 ; i++) {
            m=(a[i]>>>4)&0x0F;
            n=a[i]&0x0F;
            b[i]=Sbox[m][n];
        }
        return ByteUtil.bytes2Int(b);
    }

    /**
     * 线性变换L
     * 非线性变换τ的输出是线性变换L的输入。
     * C=L(B)=B^(B<<<2)^(B<<<10)^(B<<<18)^(B<<<24)
     *
     * 线性变换L'
     * L'(B)=B^(B<<<13)^(B<<<23)
     * @param B
     * @return
     */
    public static int L(int B,boolean is_){
        if(is_){
            return B^ByteUtil.rotateLeft(B,13)^ByteUtil.rotateLeft(B,23);
        }
        return B^ByteUtil.rotateLeft(B,2)^ByteUtil.rotateLeft(B,10)^ByteUtil.rotateLeft(B,18)^ByteUtil.rotateLeft(B,24);
    }


    /**
     * T是一个可逆的线性变换，由非线性变换τ和线性变换L复合而成，即T(.)=L(τ(.))
     * @param A
     * @return
     */
    private static int T(int A,boolean is_){
        if(is_){
            return L(τ(A),true);
        }else {
            return L(τ(A),false);
        }
    }

    /**
     * 轮函数
     * 设输入为(X0,X1,X2,X3),轮秘钥为rk，则轮函数F为：
     * F(X0,X1,X2,X3,rk) = X0^T(X1^X2^X3^rk)
     * @param rk 轮秘钥
     * @return
     */
    public static int F(int X0,int X1,int X2,int X3,int rk){
        return X0^T(X1^X2^X3^rk,false);
    }
    /**
     * 生成轮秘钥
     * @param MK 加密秘钥
     * @return
     * 加密秘钥MK=(MK(0),MK(1),MK(2),MK(3)),轮秘钥的生成方法
     * (K(0),K(1),K(2),K(3))=(MK(0)^FK(0),MK(1)^FK(1),MK(2)^FK(2),MK(3)^FK(3))
     * FK为固定系统参数
     * rk(i)=K(i+4)=K(i)^T'(K(i+1)^K(i+2)^K(i+3)^CK(i))
     * CK为固定参数 T'是将T中的线性变换L替换为L'
     * L'(B)=B^(B<<<13)^(B<<<23)
     */
    public static int[] generateRoundKey(byte[] MK){
        if(MK.length*8 != 128){
            throw new IllegalArgumentException("the key length must be 128 bit!");
        }
        int K[] = new int[36];
        for (int i=0;i<4;i++) {
            K[i]= ByteUtil.bytes2Int(Arrays.copyOfRange(MK,i*4,(i+1)*4))^FK[i];
        }
        for (int i = 0; i <32; i++) {
           K[i+4]=K[i]^T(K[i+1]^K[i+2]^K[i+3]^CK[i],true);
        }
        return Arrays.copyOfRange(K,4,K.length);
    }

    /**
     * 加密算法
     * 本加密算法由32次迭代运算和1次反序变换R组成。
     * 设明文输入为(X(0),X(1),X(2),X(3))，密文输出为(Y(0),Y(1),Y(2),Y(3))，轮秘钥为rk(i),i=0,1,2,...,31。
     * 加密算法的运算过程如下：
     * (1)32次迭代运算:X(i+4)=F(X(i),X(i+1),X(i+2),X(i+3),rk(i))
     * (2)反序变换:(Y(0),Y(1),Y(2),Y(3))=R(X(32),X(33),X(34),X(35))=(X(35),X(34),X(33),X(32))
     * @param plainText
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] plainText,byte[] key){
        if(plainText.length*8!=128){
            throw new IllegalArgumentException("the plainText length must be 128 bit!");
        }
        int[] X = new int[32];
        X[0]=ByteUtil.bytes2Int()
    }


}
