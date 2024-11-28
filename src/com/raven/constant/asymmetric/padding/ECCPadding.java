package com.raven.constant.asymmetric.padding;

public enum ECCPadding {
    ECIES("ECIES"),
    PKCS1Padding("EC/PKCS1Padding"),
    NoPadding("EC/NoPadding");

    private final String paddingType;

    ECCPadding(String paddingType) {
        this.paddingType = paddingType;
    }

    // Getter
    public String getValue() {
        return paddingType;
    }
}