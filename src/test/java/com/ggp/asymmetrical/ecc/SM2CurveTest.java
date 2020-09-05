package com.ggp.asymmetrical.ecc;

import org.junit.Test;

public class SM2CurveTest {
    @Test
    public void test_generateKeyPair(){
        System.out.println(SM2Curve.generateKeyPair());
    }

}