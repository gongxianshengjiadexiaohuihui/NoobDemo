package com.ggp.util;

import java.util.HashMap;
import java.util.Map;

public class ByteUtil {
    /**
     * long转bytes
     *
     * @param l
     * @return
     */
    public static byte[] long2Bytes(long l) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (l >>> ((7 - i) * 8));
        }
        return bytes;
    }

    /**
     * 打印字节数组的二进制显示
     *
     * @param bytes
     */
    public static void printBits(byte[] bytes) {
        Map<Integer, String> cache = new HashMap<>();
        char[] chars = new char[8];
        int b;
        for (int i = 0; i < bytes.length; i++) {
            if (i % 8 == 0) {
                System.out.println("\n");
            }
            b = bytes[i];
            int k = bytes[i];
            /**
             * 先判断缓存里有没有
             */
            if (null != cache.get(Integer.valueOf(b))) {
                System.out.print(cache.get(Integer.valueOf(b)));
                System.out.print(" ");
                continue;
            }
            /**
             * 缓存里没有就拼装
             */
            for (int j = 0; j <8 ; j++) {
                chars[7-j] = (char) (b&1+48);
                b=b>>>1;
            }
            cache.put(Integer.valueOf(k),new String(chars));
            System.out.print(new String(chars));
            System.out.print(" ");
        }
    }
}
