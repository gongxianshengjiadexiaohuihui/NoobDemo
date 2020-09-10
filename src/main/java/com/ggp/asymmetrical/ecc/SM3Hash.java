package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class SM3Hash {
    /**
     * 输出杂凑值的bit长度
     */
    public static final int OUT_LENGTH=256;
    /**
     * 1000 0000
     */
    private static final byte FIRST_PADDING = (byte) 0x80;
    /**
     * 0000 0000
     */
    private static final byte ZERO_PADDING = (byte) 0x00;

    /**
     * 常数与函数
     */

    /**
     * 初始值，用于压缩
     */
    private static final BigInteger IV = new BigInteger("7380166F4914B2B9172442D7DA8A0600A96F30BC163138AAE38DEE4DB0FB0E4E", 16);

    /**
     * 常量
     *
     * @param j
     * @return
     */
    private static int T(int j) {
        if (j >= 0 && j <= 15) {
            return Integer.valueOf("79CC4519", 16).intValue();
        } else if (j >= 16 && j <= 63) {
            return Integer.valueOf("7A879D8A", 16);
        } else {
            throw new IllegalArgumentException("the j must between (0,63)");
        }
    }

    /**
     * 布尔函数
     */
    private static int FF(int X, int Y, int Z, int j) {
        if (j >= 0 && j <= 15) {
            return X ^ Y ^ Z;
        } else if (j >= 16 && j <= 63) {
            return (X & Y) | (X & Z) | (Y & Z);
        } else {
            throw new IllegalArgumentException("the j must between (0,63)");
        }
    }

    private static int GG(int X, int Y, int Z, int j) {
        if (j >= 0 && j <= 15) {
            return X ^ Y ^ Z;
        } else if (j >= 16 && j <= 63) {
            return (X & Y) | (~X & Z);
        } else {
            throw new IllegalArgumentException("the j must between (0,63)");
        }
    }

    /**
     * 置换函数
     */
    private static int P0(int x) {
        return x ^ (ByteUtil.rotateLeft(x, 9)) ^ (ByteUtil.rotateLeft(x, 17));
    }

    private static int P1(int x) {
        return x ^ (ByteUtil.rotateLeft(x, 15)) ^ (ByteUtil.rotateLeft(x, 23));
    }

  

    public static byte[] hash(byte[] src) throws IOException{
        return iterativeCompression(padding(src));
    }

    /**
     * 填充
     * 假设消息m的长度为l(l<2^64)比特，则首先将比特“1”添加到消息的末尾，再添加k个“0”,k是满足l+1+k=448 (mod 512)的最小的非负整数。
     * 然后再添加一个64位比特串，该比特串是长度L的二进制表示，填充后的消息m’的比特长度为512的整倍数。
     *
     * @param src
     * @return
     */
    public static byte[] padding(byte[] src) throws IOException {
        if (src.length > 0x2000000000000000L) {
            throw new IllegalArgumentException("填充长度必须小于2^64 bit");
        }
        long l = src.length * 8;
        long k = 448 - (l + 1) % 512;
        if (k < 0) {
            k = k + 448;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(src);
        /**
         * 消息m一定是整数个字节，填充消息也是整数个字节，所以k个0加一个bit的1也是整数个字节
         */
        os.write(FIRST_PADDING);
        long i = (k - 7) / 8;
        for (int j = 0; j < i; j++) {
            os.write(ZERO_PADDING);
        }
        os.write(ByteUtil.long2Bytes(l));
        return os.toByteArray();
    }

    /**
     * 迭代压缩
     * 将填充后的消息m'按512比特进行分组m'= B(0)B(1)...B(n-1)
     * 其中n= = (l+k+65)/512
     * 对m'按下面方式迭代
     * <p>
     * FOR i=0 TO n-1
     * V(i+1)=CF(V(i),B(i))
     * END FOR
     * <p>
     * 其中CF是压缩函数，V(0)为256比特初始值IV，B(i)为填充后的消息分组，迭代压缩的结果为V(n)
     *
     * @param src
     * @return
     */
    public static byte[] iterativeCompression(byte[] src) {
        int n = src.length / 64;
        int[] W = new int[68];
        int[] W_ = new int[64];
        byte[] VI = IV.toByteArray();
        byte[] result = new byte[0];
        for (int i = 0; i < n; i++) {
            result = CF(W, W_, VI, Arrays.copyOfRange(src, i * 64, (i + 1) * 64));
            VI = result;
        }
        return result;
    }

    /**
     * 压缩函数
     * 令A,B,C,D,E,F,G,H为字寄存器，SS1, SS2, TT1, TT2为中间变量，压缩函数V(i+1)=CF(V(i),B(I)),0<=i<=n-1;
     * 计算过程
     * ABCDEFGH = V< i >
     * FOR  j=0  TO  63
     * SSl=((A+<12)+E+(T[j]<<<j))<<<7
     * SS2=SSl^(A<<<12)
     * TTl=FF[j](A，B，C) + D + SS2 + W'[j];
     * TT2=GG[j](E, F, G) + H + SS1 + W[j];
     * D=C
     * C=B<<<9
     * B=A
     * A=TT1
     * H=G
     * G=F<<<19
     * F=E
     * E=P[0] (TT2)
     * END FOR
     * V<i+1>=ABCDEFGH^V< i >
     * <p>
     * 其中，字的存储为big-endian格式，左边为高有效位，右边为低有效位。
     *
     * @param W
     * @param W_
     * @param VI
     * @param BI
     * @return
     */
    public static byte[] CF(int[] W, int[] W_, byte[] VI, byte[] BI) {
        messageExtend(W, W_, BI);
        int A, B, C, D, E, F, G, H, SS1, SS2, TT1, TT2;
        A = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 0, 4));
        B = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 4, 8));
        C = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 8, 12));
        D = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 12, 16));
        E = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 16, 20));
        F = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 20, 24));
        G = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 24, 28));
        H = ByteUtil.bytes2Int(Arrays.copyOfRange(VI, 28, 32));
        for (int j = 0; j < 64; j++) {
            SS1 = ByteUtil.rotateLeft((ByteUtil.rotateLeft(A, 12) + E + ByteUtil.rotateLeft(T(j), j)), 7);
            SS2 = SS1 ^ ByteUtil.rotateLeft(A, 12);
            TT1 = FF(A, B, C, j) + D + SS2 + W_[j];
            TT2 = GG(E, F, G, j) + H + SS1 + W[j];
            D = C;
            C = ByteUtil.rotateLeft(B, 9);
            B = A;
            A = TT1;
            H = G;
            G = ByteUtil.rotateLeft(F, 19);
            F = E;
            E = P0(TT2);
        }
        byte[] v = ByteUtil.intArray2bytes(new int[]{A, B, C, D, E, F, G, H});
        for (int i = 0; i < v.length; i++) {
            v[i] = (byte) (v[i] ^ VI[i]);
        }
        return v;
    }

    /**
     * 消息扩展
     * 将消息分组B按以下方法扩展生成132个消息字W[0],W[1]，W[2]…,W[67],W’[0],W’[1]，W’[2]…,W’[63]用于压缩函
     * 数CF:
     * a)将消息分组B< i >，划分为16个字W[0],W[1]，W[2],…W[15]
     * b)
     * FOR  j=16  TO  67
     * W[j] = p[1] (W[j-16]^W[j-9]^(W[j-3]<<<15))^(W[j-13]<<<7)^W[j-6]
     * END FOR
     * c)
     * FOR  j=0  TO  63
     * W'[j]= W[j]^W[j+4]
     * END FOR
     *
     * @param W
     * @param W_
     * @param B
     */
    public static void messageExtend(int[] W, int[] W_, byte[] B) {
        for (int i = 0; i < 16; i++) {
            W[i] = ByteUtil.bytes2Int(Arrays.copyOfRange(B, i * 4, (i + 1) * 4));
        }
        for (int i = 16; i < 68; i++) {
            W[i] = P1(W[i - 16] ^ W[i - 9] ^ ByteUtil.rotateLeft(W[i - 3], 15)) ^ ByteUtil.rotateLeft(W[i - 13], 7) ^ W[i - 6];
        }
        for (int i = 0; i < 64; i++) {
            W_[i] = W[i] ^ W[i + 4];
        }
    }
}
