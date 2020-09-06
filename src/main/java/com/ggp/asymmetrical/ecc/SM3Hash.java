package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SM3Hash {
    /**
     * 填充
     * 假设消息m的长度为l(l<2^64)比特，则首先将比特“1”添加到消息的末尾，再添加k个“0”,k是满足l+1+k=448 (mod 512)的最小的非负整数。
     * 然后再添加一个64位比特串，该比特串是长度L的二进制表示，填充后的消息m’的比特长度为512的整倍数。
     * @param src
     * @return
     */
    public byte[] padding(byte[] src) throws IOException {
        if(src.length > 0x2000000000000000L){
            throw new IllegalArgumentException("填充长度必须小于2^64 bit");
        }
        long l = src.length * 8;
        long k = 448 -  (l + 1)%512;
        if(k < 0){
            k = k +448;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(src);
        /**
         * 消息m一定是整数个字节，填充消息也是整数个字节，所以k个0加一个bit的1也是整数个字节
         */
        os.write((byte)0x80);
        long i = (k-7)/8;
        for (int j = 0; j < i; j++) {
            os.write((byte)0x00);
        }
        os.write(ByteUtil.long2Bytes(l));
        return os.toByteArray();
    }

}
