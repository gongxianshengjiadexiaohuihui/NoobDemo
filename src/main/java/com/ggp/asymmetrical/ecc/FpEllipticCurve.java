package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-01 13:55
 * @Description:
 * 有限域上的椭圆曲线( ≡/表示不同余)
 * Fp={y^²≡x^³+ax+b(mod p)，4a^³+27b^²≡/0(mod p)}U{0}
 */
public class FpEllipticCurve extends EllipticCurve {
    /**
     * 有限域Fp的阶
     */
    private BigInteger N;
    /**
     * Fp子群的阶
     */
    private BigInteger n;
    /**
     * 子群的辅因子
     */
    private BigInteger h;
    /**
     * 子群的基点
     */
    private EllipticCurvePoint G;

    public FpEllipticCurve(BigInteger a, BigInteger b, BigInteger n, BigInteger n1, BigInteger h, EllipticCurvePoint g) {
        super(a, b);
        N = n;
        this.n = n1;
        this.h = h;
        G = g;
    }

    public BigInteger getN() {
        return N;
    }

    public void setN(BigInteger n) {
        N = n;
    }

    public BigInteger getH() {
        return h;
    }

    public void setH(BigInteger h) {
        this.h = h;
    }

    public EllipticCurvePoint getG() {
        return G;
    }

    public void setG(EllipticCurvePoint g) {
        G = g;
    }
}
