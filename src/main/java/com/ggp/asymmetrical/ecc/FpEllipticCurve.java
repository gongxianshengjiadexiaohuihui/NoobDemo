package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-01 13:55
 * @Description: 有限域上的椭圆曲线(≡ / 表示不同余)
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

    public FpEllipticCurve(BigInteger a, BigInteger b, BigInteger P, BigInteger N, BigInteger n, BigInteger h, EllipticCurvePoint G) {
        super(a, b);
        this.P = P;
        this.N = N;
        this.n = n;
        this.h = h;
        this.G = G;
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
        return point.getY().multiply(point.getY()).mod(P)
                .compareTo(
                        point.getX().multiply(point.getX()).multiply(point.getX())
                                .add(a.multiply(point.getX()))
                                .add(b)
                                .mod(P)
                ) == 0;
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
        if (null == point) {
            return null;
        }
        EllipticCurvePoint neg = new EllipticCurvePoint(point.getX(), point.getY().negate().mod(P));
        check(neg);
        return neg;
    }

    public BigInteger multiplyInverse(BigInteger k){
        return multiplyInverse(k,P);
    }
    public BigInteger addInverse(BigInteger k){
        return addInverse(k,P);
    }

    /**
     * 求加法逆元
     * @param k
     * @param p
     * @return
     */
    public BigInteger addInverse(BigInteger k,BigInteger p){
        if(k.compareTo(BigInteger.valueOf(0L))<0){
            return p.subtract(k.abs());
        }
        return k;
    }
    /**
     * 求逆元，用于模的除法
     * (a / b) mod p= (a * b') mod p
     * b' 为 b 的逆元，b * b' mod p = 1
     * b * b' = p*k +1
     * 根据贝祖等式
     * a*x + b*y = gcd(a,b)=1
     * 就是求解x,为a的逆元
     *扩展欧几里德算法：
     * 基本算法：对于不完全为 0 的非负整数 a，b，gcd（a，b）表示 a，b 的最大公约数，必然存在整数对 x，y ，使得 ：
     *
     * gcd（a，b）=ax+by。
     *
     * 证明：设 a>b。
     *
     * 　　1，显然当 b=0，gcd（a，b）=a。此时 x=1，y=0；
     *
     * 　　2，ab!=0 时
     *
     * 　　设 ax1+by1=gcd(a,b);
     *
     * 　　bx2+(a mod b)y2=gcd(b,a mod b);
     *
     * 　　根据朴素的欧几里德原理有 gcd(a,b)=gcd(b,a mod b);
     *
     * 　　则:ax1+by1=bx2+(a mod b)y2;
     *
     * 　　即:ax1+by1=bx2+(a-(a/b)*b)y2=ay2+bx2-(a/b)*by2;
     *
     * 　　根据恒等定理得：x1=y2; y1=x2-(a/b)*y2;
     *
     *      这样我们就得到了求解 x1,y1 的方法：x1，y1 的值基于 x2，y2.
     *
     * 　  上面的思想是以递归定义的，因为 gcd 不断的递归求解一定会有个时候 b=0，所以递归可以结束。
     * @return
     */
    public BigInteger multiplyInverse(BigInteger k, BigInteger p) {
        if(k.compareTo(BigInteger.valueOf(0L)) ==0){
            throw new IllegalArgumentException("0 can not be division!");
        }
        BigInteger[] result = extended_euclidean_algorithm(k,p);
        return addInverse(result[0],p);
    }
    private BigInteger[]  extended_euclidean_algorithm(BigInteger a,BigInteger b){
        if(b.equals(BigInteger.valueOf(0L))){
            BigInteger[] result = new BigInteger[2];
            result[0]=BigInteger.valueOf(1L);
            result[1]=BigInteger.valueOf(0L);
            return result;
        }
        BigInteger[] result=extended_euclidean_algorithm(b,a.mod(b));
        BigInteger temp= result[0];
        result[0]=result[1];
        result[1]=temp.subtract(a.divide(b).multiply(result[1]));
        return result;
    }


    /**
     * 点加
     *
     * @param p
     * @param q
     * @return
     */
    @Override
    public EllipticCurvePoint pointAdd(EllipticCurvePoint p, EllipticCurvePoint q) {
        check(p);
        check(q);
        BigInteger m, x, y;
        /**
         * o+point=point;
         */
        if (null == p) {
            return q;
        }
        if (null == q) {
            return p;
        }
        /**
         * 如果点关于a,b关于x轴对称a+b=0
         */
        if (p.getX().equals(q.getX()) && !p.getY().equals(q.getY())) {
            return null;
        }
        /**
         * p == q
         */
        if (p.getX().equals(q.getX())) {
            m = p.getX().multiply(p.getX()).multiply(BigInteger.valueOf(3L)).add(a)
                    .multiply(this.multiplyInverse(p.getY().multiply(p.getY())));
        } else {
            /**
             * p != q
             */
            m = q.getY().subtract(p.getY()).multiply(this.multiplyInverse(q.getX().subtract(p.getX())));
        }
        x = m.multiply(m).subtract(p.getX()).subtract(q.getX()).mod(P);
        y = p.getY().add(m.multiply(x.subtract(p.getX()))).mod(P);
        y = y.negate();
        EllipticCurvePoint point = new EllipticCurvePoint(x, y);
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
