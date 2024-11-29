package com.raven.service.classical;

import javax.crypto.SecretKey;

public interface IClassicalService {
    boolean requireKey();
    String generateKey(int length);
    String decrypt(String inputText);
    String encrypt(String inputText);
}
