package com.ggp.noob.demo.algorithm.asymmetrical.ecc;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

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

    /**
     * 有这么一个场景；
     *    Alice想要用她的私钥d_A签名，Bob想要用Alice的公钥H_A要验证签名，只有Alice才能提供正确的签名，而每个人都可以验证签名。
     * ECDSA是DSA作用于椭圆曲线的一个变种算法。Alice和Bob仍然使用同样的曲线，ECDSA需要使用明文的哈希结果，而不是明文本身。哈希函数
     * 的选择取决于使用者，但是需要明确的是必须选择加密安全的哈希函数，为了使哈希结果的比特长度和n(子群的阶)的比特长度一致，消息的哈希
     * 结果需要被截断，被截断后的哈希值是一个整数，我们用Z表示。
     *
     * Alice使用算法来签名的步骤如下：
     * 1.在{1,...,n-1}范围内选择一个随机数k（n是子群的阶）
     * 2.计算点P=k*G(G是子群的基点)
     * 3.计算数字r=P_x mod n (P_x是P的x轴坐标)
     * 4.如果r=0,另选一个k并重新计算
     * 5.计算s=k^-1*(z+r*d_A) mod n(d_A是Alice的私钥，k^-1是k mod n的乘法逆元)
     * 6.如果s=0，另选一个k并重新计算
     *
     * (r,s)就是签名
     *
     * Bob使用算法验证签名
     *  准备材料Alice的公钥H_A,被截断的哈希值z，还有签名(r,s)
     *  1.计算整数u1=s^-1*z mod n
     *  2.计算整数u2=s^-1*r mod n
     *  3.计算点P=u1*G+u2*H_A
     *
     *  只有当r=P_X mod n 的时候，签名才被验证成功
     *
     *  算法正确性
     *   P=u1*G+u2*H_A 因为H_A=d_A*G，所以
     *   P=u1*G+u2*d_A*G
     *    =(u1+u2*d_A)G
     *   通过u1和u2的定义，可以得到
     *   P=(u1+u2*d_A)G
     *    =(s^-1*z+s^-1*r*d_A)G
     *    =s^-1*(z+r*d_A)G
     *    根据生成签名步骤第5步，知道s=k^-1*(z+r*d_A) mod n，等式两边都乘以k，在除以s得到
     *    k=s^-1*(z+r*d_A)带入上式子得到
     *   P=k*G
     *   这样就得到了和签名时一样的P点
     */
    @Test
    public void test_ECDSA(){
        BigInteger z = BigInteger.valueOf(123L);
        BigInteger r,s;
        ECKeyPair keyPair = SM2Curve.generateKeyPair();
        /**
         * 签名
         */
        end:while(true) {
           restart: while(true) {
                BigInteger k = new BigInteger(SM2Curve.length, new Random());
                EllipticCurvePoint P = SM2Curve.curve.scalar_multi(k, SM2Curve.G);
                r = P.getX().mod(SM2Curve.n);
                if(r.equals(BigInteger.valueOf(0L))){
                    break restart;
                }
                s = SM2Curve.curve.multiplyInverse(k,SM2Curve.n).multiply(z.add(r.multiply(keyPair.getPrivateKey().getK())));
                if(s.equals(BigInteger.valueOf(0L))){
                    break restart;
                }
                break end;
            }
        }
        /**
         * 验签
         */
        BigInteger u1 = SM2Curve.curve.multiplyInverse(s,SM2Curve.n).multiply(z);
        BigInteger u2 = SM2Curve.curve.multiplyInverse(s,SM2Curve.n).multiply(r);
        EllipticCurvePoint P =SM2Curve.curve.pointAdd(SM2Curve.curve.scalar_multi(u1,SM2Curve.G),SM2Curve.curve.scalar_multi(u2,keyPair.getPublicKey().getPoint()));
        Assert.assertEquals(r,P.getX().mod(SM2Curve.n));
    }
    @Test
    public void test_sign_verify()throws Exception{
        ECKeyPair keyPair = SM2Curve.generateKeyPair();
        byte[] ID_A = "GGP".getBytes();
        byte[] src = "test".getBytes();
        byte[] sign = SM2Curve.sign(src,ID_A,keyPair.getPrivateKey());
        boolean result = SM2Curve.verify(src,sign,ID_A,keyPair.getPublicKey());
        Assert.assertTrue(result);

    }
    @Test
    public void test(){
        ECKeyPair keyPair = SM2Curve.generateKeyPair();
        System.out.println(keyPair.getPublicKey().getPoint());
        System.out.println(new EllipticCurvePoint(keyPair.getPublicKey().getPoint().getBytes()));
    }
}