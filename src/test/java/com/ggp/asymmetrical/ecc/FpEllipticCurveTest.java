package com.ggp.asymmetrical.ecc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

/**
 * @Author:ggp
 * @Date:2020-09-02 19:46
 * @Description:
 */
public class FpEllipticCurveTest {
    @Test
    public void test_on_curve(){
        EllipticCurvePoint G = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(2L),BigInteger.valueOf(3L),BigInteger.valueOf(97),BigInteger
        .valueOf(5L),BigInteger.valueOf(1L),G);
        Assert.assertTrue(curve.onCurve(G));
    }
    @Test
    public void test_inverse_mod(){
        EllipticCurvePoint G = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(2L),BigInteger.valueOf(3L),BigInteger.valueOf(97),BigInteger
                .valueOf(5L),BigInteger.valueOf(1L),G);
        BigInteger inverse1 = BigInteger.valueOf(-77L).modInverse(BigInteger.valueOf(97L));
        BigInteger inverse = curve.multiplyInverse(BigInteger.valueOf(-77L),BigInteger.valueOf(97L));
        System.out.println(inverse);
        Assert.assertEquals(inverse,inverse1);
    }
    @Test
    public void test_point_add(){
        EllipticCurvePoint G = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(1L),BigInteger.valueOf(1L),BigInteger.valueOf(23),BigInteger
                .valueOf(23L),BigInteger.valueOf(1L),G);
        EllipticCurvePoint p = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(10L));
        EllipticCurvePoint q = new EllipticCurvePoint(BigInteger.valueOf(9L),BigInteger.valueOf(7L));
        EllipticCurvePoint r = new EllipticCurvePoint(BigInteger.valueOf(17L),BigInteger.valueOf(20L));
        EllipticCurvePoint p2 = new EllipticCurvePoint(BigInteger.valueOf(7L),BigInteger.valueOf(12L));
        EllipticCurvePoint result = curve.pointAdd(p,q);
        Assert.assertEquals(r,result);
        result = curve.pointAdd(p,p);
        Assert.assertEquals(p2,result);
    }
    @Test
    public void test_scalar_multi(){
        EllipticCurvePoint G = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(2L),BigInteger.valueOf(3L),BigInteger.valueOf(97L),BigInteger
                .valueOf(23L),BigInteger.valueOf(1L),G);
        EllipticCurvePoint p = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        for (int i = 0; i <6 ; i++) {
            Assert.assertEquals(curve.scalar_multi(BigInteger.valueOf(i),p),curve.scalar_multi(BigInteger.valueOf(i+5),p));
        }
    }
    @Test
    public void test_generateKeyPair(){
        BigInteger x = new BigInteger("79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798",16);
        BigInteger y = new BigInteger("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8",16);
        EllipticCurvePoint G = new EllipticCurvePoint(x,y);
        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F",16);
        BigInteger n = new BigInteger("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141",16);
        BigInteger h = BigInteger.valueOf(1L);
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(0L),BigInteger.valueOf(7L),p,n,h,G);
        Random random = new Random();
        System.out.println(curve.generateKeyPair(new BigInteger(256,random)));
    }
    @Test
    public void test(){
        Random random = new Random();
        System.out.println((new BigInteger(256,random)));
    }
}