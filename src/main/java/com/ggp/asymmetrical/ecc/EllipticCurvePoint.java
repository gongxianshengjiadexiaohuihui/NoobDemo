package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;

/**
 * @Author:ggp
 * @Date:2020-09-01 13:56
 * @Description:
 */
public class EllipticCurvePoint {
    /**
     * 点的横坐标
     */
    private BigInteger x;
    /**
     * 点的纵坐标
     */
    private BigInteger y;

    public EllipticCurvePoint(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EllipticCurvePoint){
            EllipticCurvePoint point = (EllipticCurvePoint)obj;
            if(this.x .equals(point.getX()) && this.getY().equals(point.getY()) ){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "EllipticCurvePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
