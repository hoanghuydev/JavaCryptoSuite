package com.raven.controller;

public interface ICipherSwitchable<T> {
    T getCipher();
    void setCipher(T cipher);

}
