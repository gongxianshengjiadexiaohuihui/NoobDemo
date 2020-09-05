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
        EllipticCurvePoint expect = new EllipticCurvePoint(BigInteger.valueOf(-3L),BigInteger.valueOf(2L));
        Assert.assertEquals(expect,curve.pointAdd(p,q));

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
        Assert.assertEquals(p2,curve.scalar_multi(BigInteger.valueOf(2),p));
    }
}