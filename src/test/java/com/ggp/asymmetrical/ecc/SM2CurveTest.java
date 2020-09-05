package com.ggp.asymmetrical.ecc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class SM2CurveTest {
    @Test
    public void test_generateKeyPair(){
        BigInteger k = new BigInteger("81EB26E941BB5AF16DF116495F90695272AE2CD63D6C4AE1678418BE48230029",16);
        BigInteger x = new BigInteger("160e12897df4edb61dd812feb96748fbd3ccf4ffe26aa6f6db9540af49c94232",16);
        BigInteger y = new BigInteger("4a7dad08bb9a459531694beb20aa489d6649975e1bfcf8c4741b78b4b223007f",16);
        ECPublicKey publicKey = new ECPublicKey(x,y);
        ECKeyPair keyPair =SM2Curve.generateKeyPair(k);
        Assert.assertEquals(publicKey,keyPair.getPublicKey());
    }

}