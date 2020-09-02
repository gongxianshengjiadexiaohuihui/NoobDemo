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

}