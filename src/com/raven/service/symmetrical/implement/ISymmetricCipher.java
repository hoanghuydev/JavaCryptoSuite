package com.raven.service.symmetrical.implement;

import javax.crypto.SecretKey;

public interface ISymmetricCipher {
    SecretKey generateSecretKey(int length) throws Exception;
    String decryptFromBase64(String base64Text) throws Exception;
    void decryptFile(String srcFile, String destFile) throws Exception;
    String encryptToBase64(String plainText) throws Exception;
    void encryptFile(String srcFile, String destFile) throws Exception;
    String exportKey() throws Exception;
    SecretKey importKey(String keyText) throws Exception;

}
