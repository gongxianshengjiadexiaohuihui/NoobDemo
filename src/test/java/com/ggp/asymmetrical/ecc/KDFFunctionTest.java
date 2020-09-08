package com.ggp.asymmetrical.ecc;

import com.ggp.util.ByteUtil;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-08 09:55
 * @Description:
 */
public class KDFFunctionTest {
    @Test
    public void test_KDF() throws Exception{
        BigInteger x = new BigInteger("57E7B63623FAE5F08CDA468E872A20AFA03DED41BF140377",16);
        BigInteger y = new BigInteger("0E040DC83AF31A67991F2B01EBF9EFD8881F0A0493000603",16);
        int klen=152;
        byte[] t = KDFFunction.KDF(ByteUtil.bytesArray2bytes(x.toByteArray(),y.toByteArray()),klen);
       // byte[] t = SM3Hash.hash(ByteUtil.bytesArray2bytes(x.toByteArray(),y.toByteArray()));
        ByteUtil.print(t,16);
    }
}