package com.ggp.noob.demo.algorithm.asymmetrical.ecc;

public class ECKeyPair {
    private ECPublicKey publicKey;
    private ECPrivateKey privateKey;

    public ECKeyPair(ECPublicKey publicKey, ECPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public ECPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ECPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public ECPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(ECPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "ECKeyPair{" +
                "publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                '}';
    }
}
