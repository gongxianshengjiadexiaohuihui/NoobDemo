package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;

public class ECPrivateKey {
    private BigInteger x;
    private BigInteger y;
    private BigInteger k;

    public ECPrivateKey(EllipticCurvePoint point,BigInteger k){
        this.x = point.getX();
        this.y = point.getY();
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

    @Override
    public String toString() {
        return "ECPrivateKey{" +
                "x=" + x +
                ", y=" + y +
                ", k=" + k +
                '}';
    }
}
