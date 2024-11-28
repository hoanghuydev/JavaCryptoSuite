package com.raven.controller;

public interface IModeSwitchable {
    default void toggleMode() {
        setIsEncryptMode(!isEncryptMode());
    }

    boolean isEncryptMode();

    void setIsEncryptMode(boolean isEncrypt);
}


