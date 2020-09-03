package com.ggp.asymmetrical.ecc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-01 14:27
 * @Description:
 */
public class EllipticCurveTest {
    @Test
    public void test_on_curve(){
        EllipticCurve curve = new EllipticCurve(BigInteger.valueOf(-7L),BigInteger.valueOf(10L));
        EllipticCurvePoint point = new EllipticCurvePoint(BigInteger.valueOf(1L),BigInteger.valueOf(2L));
        EllipticCurvePoint infinite = null;
        Assert.assertTrue(curve.onCurve(point));
        Assert.assertTrue(curve.onCurve(infinite));
    }
    @Test
    public void test_point_add(){
        EllipticCurve curve = new EllipticCurve(BigInteger.valueOf(-7L),BigInteger.valueOf(10L));
        EllipticCurvePoint p = new EllipticCurvePoint(BigInteger.valueOf(1L),BigInteger.valueOf(2L));
        Assert.assertEquals(p,curve.pointAdd(p,null));
        Assert.assertEquals(p,curve.pointAdd(null,p));
        EllipticCurvePoint q = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(4L));
        System.out.println(curve.pointAdd(p,q));

    }
    @Test
    public void test_point_neg(){
        EllipticCurve curve = new EllipticCurve(BigInteger.valueOf(-7L),BigInteger.valueOf(10L));
        EllipticCurvePoint p = new EllipticCurvePoint(BigInteger.valueOf(1L),BigInteger.valueOf(2L));
        EllipticCurvePoint neg = curve.neg(p);
        Assert.assertEquals(p.getX(),neg.getX());
        Assert.assertEquals(p.getY().add(neg.getY()),BigInteger.valueOf(0L));
    }
    @Test
    public void test_point_scalar_multi(){
        EllipticCurve curve = new EllipticCurve(BigInteger.valueOf(-7L),BigInteger.valueOf(10L));
        EllipticCurvePoint p = new EllipticCurvePoint(BigInteger.valueOf(1L),BigInteger.valueOf(2L));
        EllipticCurvePoint p2 = new EllipticCurvePoint(BigInteger.valueOf(-1L),BigInteger.valueOf(-4L));
        System.out.println(curve.scalar_multi(BigInteger.valueOf(2),p));
    }
    @Test
    public void test_test(){
        BigInteger x = BigInteger.valueOf(9L);
        System.out.println(x.modInverse(BigInteger.valueOf(23L)));
        BigInteger i = new BigInteger("5");
        System.out.println(i.bitLength());
        System.out.println(i.and(BigInteger.valueOf(1L)));
        i=i.shiftRight(1);
        System.out.println(i.bitLength());
        System.out.println(i.bitCount());
        System.out.println(i.and(BigInteger.valueOf(1L)));
    }
}