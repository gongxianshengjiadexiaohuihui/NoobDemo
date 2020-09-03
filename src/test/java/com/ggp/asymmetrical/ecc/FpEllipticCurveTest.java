package com.ggp.asymmetrical.ecc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-02 19:46
 * @Description:
 */
public class FpEllipticCurveTest {
    @Test
    public void test_on_curve(){
        EllipticCurvePoint G = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(2L),BigInteger.valueOf(3L),BigInteger.valueOf(97),BigInteger.valueOf(2L),BigInteger
        .valueOf(5L),BigInteger.valueOf(1L),G);
        Assert.assertTrue(curve.onCurve(G));
    }
    @Test
    public void test_inverse_mod(){
        EllipticCurvePoint G = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(2L),BigInteger.valueOf(3L),BigInteger.valueOf(97),BigInteger.valueOf(2L),BigInteger
                .valueOf(5L),BigInteger.valueOf(1L),G);
        BigInteger inverse1 = BigInteger.valueOf(31L).modInverse(BigInteger.valueOf(8L));
        BigInteger inverse = curve.multiplyInverse(BigInteger.valueOf(31),BigInteger.valueOf(8));
        Assert.assertEquals(inverse,inverse1);
    }
    @Test
    public void test_point_add(){
        EllipticCurvePoint G = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(6L));
        FpEllipticCurve curve = new FpEllipticCurve(BigInteger.valueOf(1L),BigInteger.valueOf(1L),BigInteger.valueOf(23),BigInteger.valueOf(2L),BigInteger
                .valueOf(23L),BigInteger.valueOf(1L),G);
        EllipticCurvePoint p = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(10L));
        EllipticCurvePoint q = new EllipticCurvePoint(BigInteger.valueOf(9L),BigInteger.valueOf(7L));
        EllipticCurvePoint result = curve.pointAdd(p,q);
        System.out.println(result);
    }
}