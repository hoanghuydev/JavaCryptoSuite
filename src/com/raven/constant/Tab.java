package com.raven.constant;

public enum Tab {
    FILE(0),
    TEXT(1);
    private final int value;

    Tab(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Tab fromValue(int value) {
        for (Tab tab : Tab.values()) {
            if (tab.getValue() == value) {
                return tab;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}