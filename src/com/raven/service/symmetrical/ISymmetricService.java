package com.raven.service.symmetrical;

import javax.crypto.SecretKey;

public interface ISymmetricService {
    SecretKey generateSecretKey(int length) throws Exception;
    String decrypt(String base64Text) throws Exception;
    void decryptFile(String srcFile, String destFile) throws Exception;
    String encrypt(String plainText) throws Exception;
    void encryptFile(String srcFile, String destFile) throws Exception;
    String exportKey() throws Exception;
    SecretKey importKey(String keyText) throws Exception;
    void setTransformation(String transformation);

}
