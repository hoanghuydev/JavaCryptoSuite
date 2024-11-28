package com.raven.constant.asymmetric.key;

public enum AsymmetricKeySize {
    SIZE_512("512"),
    SIZE_1024("1024"),
    SIZE_2048("2048");

    private final String size;

    AsymmetricKeySize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}