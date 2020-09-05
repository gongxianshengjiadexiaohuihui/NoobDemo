package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;

public class ECPublicKey {
    private BigInteger x;
    private BigInteger y;

    public ECPublicKey(EllipticCurvePoint point){
        this.x=point.getX();
        this.y=point.getY();
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
        return "ECPublicKey{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
