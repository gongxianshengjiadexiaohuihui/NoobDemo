package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-01 11:29
 * @Description: 椭圆曲线曲线
 * FR^2={y^2=x^3+ax+b，4a^3+27b^2≠0}U{0}
 * 0为无穷远点，表示为null
 * 斜率公式，p1,p2两点,p1,p2所在直线与曲线的焦点为p3
 * 如果p1!=p2
 * 斜率m=(y²-y¹)/(x²-x¹)
 * 如果p1==p2
 * 斜率m=(3X¹^2+a)/2y¹ (隐函数求导)
 *
 * y³=y²+m(x³-x²)
 * x³=m^2-x¹-x² （卡丹公式求一元三次方程解）
 */
public class EllipticCurve {
    /**
     * 系数a
     */
    protected BigInteger a;
    /**
     * 系数b
     */
    protected BigInteger b;

    public EllipticCurve(BigInteger a, BigInteger b) {
        this.a = a;
        this.b = b;
    }

    /**
     * 判断点是否在曲线上
     * @param point
     * @return
     */
    public boolean onCurve(EllipticCurvePoint point) {
        /**
         * 定义null为无穷远点
         */
        if (null == point) {
            return true;
        }
        return point.getY().multiply(point.getY())
                .compareTo(
                        point.getX().multiply(point.getX()).multiply(point.getX())
                                .add(a.multiply(point.getX()))
                                .add(b)
                ) == 0;
    }

    /**
     * 取点的相反点，椭圆曲线关于x轴对称
     * @param point
     * @return
     */
    public EllipticCurvePoint neg(EllipticCurvePoint point){
        check(point);
        if(null == point){
            return null;
        }
        EllipticCurvePoint neg = new EllipticCurvePoint(point.getX(), point.getY().negate());
        check(neg);
        return neg;

    }

    /**
     * 点加
     * @param p
     * @param q
     * @return
     */
    public EllipticCurvePoint add(EllipticCurvePoint p,EllipticCurvePoint q){
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
     * @param k
     * @param point
     */
    public EllipticCurvePoint scalar_multi(BigInteger k,EllipticCurvePoint point){
        check(point);
        if(k.equals(BigInteger.valueOf(0L))){
            throw new IllegalArgumentException("the k can not is zero");
        }
        if(k.compareTo(BigInteger.valueOf(0L))<0){
            return scalar_multi(k.negate(),neg(point));
        }
        /**
         * 位乘
         */
        EllipticCurvePoint result = null;
        EllipticCurvePoint append = point;
        while(k.bitLength()>0){
            if(k.and(BigInteger.valueOf(1L)).intValue()==1){
                result = add(result,append);
            }else{
                append= add(append,append);
            }
            k=k.shiftRight(1);
        }
        return result;
    }
    protected void check(EllipticCurvePoint point) {
        if(!onCurve(point)){
            throw new RuntimeException("the  point is not on curve");
        }
    }
    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        a = a;
    }

    public BigInteger getB() {
        return b;
    }

    public void setB(BigInteger b) {
        b = b;
    }
}