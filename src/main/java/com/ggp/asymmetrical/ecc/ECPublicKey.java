package com.ggp.asymmetrical.ecc;

import java.math.BigInteger;

public class ECPublicKey {
    private BigInteger x;
    private BigInteger y;

    public ECPublicKey(EllipticCurvePoint point){
        this.x=point.getX();
        this.y=point.getY();
    }
    public ECPublicKey(BigInteger x,BigInteger y){
        this.x = x;
        this.y = y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECPublicKey publicKey = (ECPublicKey) o;
        return x.equals(publicKey.x) &&
                y.equals(publicKey.y);
    }

}
