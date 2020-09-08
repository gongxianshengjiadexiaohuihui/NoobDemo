package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * @Author:ggp
 * @Date:2020-09-08 09:24
 * @Description: 秘钥派生函数
 * 秘钥派生函数的作用是从一个共享的秘密比特串中派生出秘钥数据。在秘钥协商过程中，秘钥派生函数
 * 的作用在秘钥交换所获得共享的秘密比特串上，从中产生所需的会话秘钥或进一步加密所需的秘钥数据
 * <p>
 * 派生过程
 * 设密码杂凑函数为Hv(),其输出是恰为长度为v比特的杂凑值。
 * <p>
 * 秘钥派生函数KDF(Z,klen):
 * 输入：比特串Z,整数klen(表示要获得的秘钥数据的比特长度，要求该值小于(2^32-1)v)
 * 输出：长度为klen的秘钥数据比特串K
 * a) 初始化一个32比特构成的计数器ct=0x00000001
 * b) 对i从1到[klen/v]执行
 * b.1) 计算 Ha(i) = Hv(Z||ct)
 * b.2)ct++
 * c) 若klen/v是整数，令Ha![klen/v]=Ha[klen/v],
 * 否则令Ha![klen/v]为Ha[klen/v]最左边的(klen - (v*[klen/v]))比特；
 * d) 令K=Ha(1)||Ha(2)||...||Ha([klen/v]-1)||Ha![klen/v]
 */
public class KDFFunction {
    public static int v = SM3Hash.OUT_LENGTH;

    public static byte[] KDF(byte[] Z, int klen) throws Exception {
        int ct = 1;
        ByteArrayOutputStream K = new ByteArrayOutputStream();
        byte[] Ha = new byte[0];
        /**
         * 避免klen<v,所以计算Ha(1)
         */
        if (klen / v == 0) {
            Ha = SM3Hash.hash(ByteUtil.bytesArray2bytes(Z, ByteUtil.int2Bytes(ct)));
            K.write(Arrays.copyOfRange(Ha, 0, klen/8));
        } else {
            for (int i = 1; i <= klen / v; i++) {
                Ha = SM3Hash.hash(ByteUtil.bytesArray2bytes(Z, ByteUtil.int2Bytes(ct)));
                K.write(Ha);
                ct++;
            }
            if (klen % v == 0) {
                K.write(Ha);
            } else {
                K.write(Arrays.copyOfRange(Ha, 0, (klen - (v * (klen / v))/8)));
            }
        }
        return K.toByteArray();
    }
}
