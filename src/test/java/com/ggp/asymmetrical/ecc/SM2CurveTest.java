package com.ggp.asymmetrical.ecc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.spec.ECPoint;

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

    /**
     * Alice 和 Bob 生成各自的私钥和公钥，Alice 的私钥为 d_a ，公钥为 H_a= d_a * G 。Bob 的私钥为 d_B ，公钥为 H_b = d_b * G ，注意，Alice 和 Bob 需要使用一样的主要参数：在同一条曲线的同一个有限域上选择一样的基点 G。
     * Alice 和 Bob 通过不安全信道交换各自的公钥H_a 和 H_b ，中间人可以窃听到 H_a 和 H_b  ，但是在无法攻破离散对数难题的情况下无法得到 d_a 和 d_b。
     * Alice 计算 share = d_a * H_b = d_a * d_b * G （使用自身的私钥和 Bob 的公钥），Bob 计算 share = d_b * H_a = d_b * d_a * G （使用自身的私钥和 Alice 的公钥），双方求得的 S 是一样的
     */
    @Test
    public void test_ECDH(){
        ECKeyPair Alice = SM2Curve.generateKeyPair();
        ECKeyPair Bob = SM2Curve.generateKeyPair();
        EllipticCurvePoint AliceShare = SM2Curve.curve.scalar_multi(Alice.getPrivateKey().getK(),Bob.getPublicKey().getPoint());
        EllipticCurvePoint BobShare = SM2Curve.curve.scalar_multi(Bob.getPrivateKey().getK(),Alice.getPublicKey().getPoint());
        Assert.assertEquals(AliceShare,BobShare);
    }
    @Test
    public void test_encrypt_decrypt()throws Exception{
        ECKeyPair keyPair = SM2Curve.generateKeyPair();
        String  src = "test";
        byte[] cipher = SM2Curve.encrypt(src.getBytes(),keyPair.getPublicKey());
        byte[] plain = SM2Curve.decrypt(cipher,keyPair.getPrivateKey());
        Assert.assertEquals(src,new String(plain));
    }
    @Test
    public void test(){
        ECKeyPair keyPair = SM2Curve.generateKeyPair();
        System.out.println(keyPair.getPublicKey().getPoint());
        System.out.println(new EllipticCurvePoint(keyPair.getPublicKey().getPoint().getBytes()));
    }
}