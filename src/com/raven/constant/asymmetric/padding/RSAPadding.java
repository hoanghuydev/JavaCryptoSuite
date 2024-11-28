package com.raven.constant.asymmetric.padding;

public enum RSAPadding {
    NO_PADDING("RSA/ECB/NoPadding"),
    PKCS1("RSA/ECB/PKCS1Padding"),
    OAEP_SHA1("RSA/ECB/OAEPWithSHA-1AndMGF1Padding"),
    OAEP_SHA256("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

    private final String transformation;

    RSAPadding(String transformation) {
        this.transformation = transformation;
    }

    public String getValue() {
        return transformation;
    }
}