package com.ggp.noob.demo.algorithm.asymmetrical.ecc;

import java.math.BigInteger;

public class ECPublicKey {
    private BigInteger x;
    private BigInteger y;
    private EllipticCurvePoint point;

    public ECPublicKey(EllipticCurvePoint point){
        this.x=point.getX();
        this.y=point.getY();
        this.point = point;
    }
    public ECPublicKey(BigInteger x,BigInteger y){
        this.x = x;
        this.y = y;
        this.point = new EllipticCurvePoint(x,y);
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

    public EllipticCurvePoint getPoint() {
        return point;
    }

    public void setPoint(EllipticCurvePoint point) {
        this.point = point;
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
