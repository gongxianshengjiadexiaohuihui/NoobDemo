package com.ggp.noob.demo.algorithm.asymmetrical.ecc;


import com.ggp.noob.util.common.bytes.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @Author:ggp
 * @Date:2020-09-01 13:56
 * @Description:
 */
public class EllipticCurvePoint {
    private static final byte[] ZERO = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
    /**
     * 点的横坐标
     */
    private BigInteger x;
    /**
     * 点的纵坐标
     */
    private BigInteger y;

    /**
     * 点格式
     * 0表示不压缩形式表示
     * 1表示压缩形式表示
     * 2表示混合表示形式
     */
    private int format = 0;

    public EllipticCurvePoint(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
    public EllipticCurvePoint(byte[] bytes){

        byte[] flag = Arrays.copyOfRange(bytes,0,1);
        if(flag[0] == 0x04 ) {
            if(bytes.length != 65){
                throw new RuntimeException("the length is not 65!");
            }
            this.x = new BigInteger(1,Arrays.copyOfRange(bytes, 1, 33));
            this.y = new BigInteger(1,Arrays.copyOfRange(bytes, 33, 65));
        }
        //todo 压缩和混合
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

    public byte[] getBytes(){
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if(format == 0){
                os.write(0X04);
                os.write(ByteUtil.toUnsigned(x));
                os.write(ByteUtil.toUnsigned(y));
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "EllipticCurvePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
