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
        EllipticCurve curve = new EllipticCurve(BigInteger.valueOf(-7L),BigInteger.valueOf(10));
        EllipticCurvePoint p = new EllipticCurvePoint(BigInteger.valueOf(1L),BigInteger.valueOf(2L));
        Assert.assertEquals(p,curve.add(p,null));
        Assert.assertEquals(p,curve.add(null,p));
        EllipticCurvePoint q = new EllipticCurvePoint(BigInteger.valueOf(3L),BigInteger.valueOf(4L));
        System.out.println(curve.add(p,q));

    }
}