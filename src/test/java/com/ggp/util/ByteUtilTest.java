package com.ggp.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author:ggp
 * @Date:2020-09-07 11:41
 * @Description:
 */
public class ByteUtilTest {
    @Test
    public void test_bytes_to_int(){
        byte[] bytes = new byte[4];
        bytes[0]=15;
        bytes[1]=0;
        bytes[2]=1;
        bytes[3]=1;
        Assert.assertEquals(251658497,ByteUtil.bytes2Int(bytes));
    }

}