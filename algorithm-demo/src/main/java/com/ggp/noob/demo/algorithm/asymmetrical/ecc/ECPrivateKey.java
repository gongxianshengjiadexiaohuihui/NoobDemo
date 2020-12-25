package com.ggp.noob.demo.algorithm.asymmetrical.ecc;

import java.math.BigInteger;
import java.util.Objects;

public class ECPrivateKey {
    private BigInteger x;
    private BigInteger y;
    private EllipticCurvePoint point;
    private BigInteger k;

    public ECPrivateKey(EllipticCurvePoint point,BigInteger k){
        this.x = point.getX();
        this.y = point.getY();
        this.point = point;
        this.k = k;
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

    public BigInteger getK() {
        return k;
    }

    public void setK(BigInteger k) {
        this.k = k;
    }

    public EllipticCurvePoint getPoint() {
        return point;
    }

    public void setPoint(EllipticCurvePoint point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "ECPrivateKey{" +
                "x=" + x +
                ", y=" + y +
                ", k=" + k +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECPrivateKey that = (ECPrivateKey) o;
        return x.equals(that.x) &&
                y.equals(that.y) &&
                k.equals(that.k);
    }

}
