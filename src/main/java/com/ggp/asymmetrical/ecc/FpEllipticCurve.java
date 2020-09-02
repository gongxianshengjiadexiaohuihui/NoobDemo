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
     * 有限域的域
     */
    private BigInteger P;
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

    public FpEllipticCurve(BigInteger a, BigInteger b,BigInteger P, BigInteger N, BigInteger n, BigInteger h, EllipticCurvePoint G) {
        super(a, b);
        P=P;
        N = N;
        n = n;
        h = h;
        G = G;
    }

    /**
     * 判断点是否在曲线上
     *
     * @param point
     * @return
     */
    @Override
    public boolean onCurve(EllipticCurvePoint point) {
        /**
         * 定义null为无穷远点
         */
        if (null == point) {
            return true;
        }
        return point.getY().multiply(point.getY())
                .subtract(
                        point.getX().multiply(point.getX()).multiply(point.getX())
                                .add(a.multiply(point.getX()))
                                .add(b)
                ).mod(P).equals(BigInteger.valueOf(0L));
    }

    /**
     * 取点的相反点，椭圆曲线关于x轴对称
     *
     * @param point
     * @return
     */
    @Override
    public EllipticCurvePoint neg(EllipticCurvePoint point) {
        check(point);
        if(null == point){
            return null;
        }
        EllipticCurvePoint neg = new EllipticCurvePoint(point.getX(), point.getY().negate().mod(P));
        check(neg);
        return neg;
    }

    /**
     * 求逆元，用于模的除法
     * @return
     */
    public EllipticCurvePoint inverseMod(BigInteger k){
        return null;
    }

    /**
     * 点加
     *
     * @param p
     * @param q
     * @return
     */
    @Override
    public EllipticCurvePoint add(EllipticCurvePoint p, EllipticCurvePoint q) {
        check(p);
        check(q);
        BigInteger m,x,y;
        /**
         * o+point=point;
         */
        if(null == p){
            return q;
        }
        if(null == q){
            return p;
        }
        /**
         * 如果点关于a,b关于x轴对称a+b=0
         */
        if(p.getX().equals(q.getX())&&!p.getY().equals(q.getY())){
            return null;
        }
        /**
         * p == q
         */
        if(p.getX().equals(q.getX())){
            m=p.getX().multiply(p.getX()).multiply(BigInteger.valueOf(3L)).add(a)
                    .divide(p.getY().multiply(p.getY()));
        }else{
            /**
             * p != q
             */
            m=q.getY().subtract(p.getY()).divide(q.getX().subtract(p.getX()));
        }
        x=m.multiply(m).subtract(p.getX()).subtract(q.getX());
        y=p.getY().add(m.multiply(x.subtract(p.getX())));
        y=y.negate();
        EllipticCurvePoint point = new EllipticCurvePoint(x,y);
        check(point);
        return point;
    }

    /**
     * 求点的标量积(k倍点)
     *
     * @param k
     * @param point
     */
    @Override
    public EllipticCurvePoint scalar_multi(BigInteger k, EllipticCurvePoint point) {
        return super.scalar_multi(k, point);
    }
    

    public BigInteger getP() {
        return P;
    }

    public void setP(BigInteger p) {
        P = p;
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
        h = h;
    }

    public EllipticCurvePoint getG() {
        return G;
    }

    public void setG(EllipticCurvePoint g) {
        G = g;
    }

    @Override
    public String toString() {
        return "FpEllipticCurve{" +
                "P=" + P +
                ", N=" + N +
                ", n=" + n +
                ", h=" + h +
                ", G=" + G +
                ", a=" + a +
                ", b=" + b +
                '}';
    }
}
