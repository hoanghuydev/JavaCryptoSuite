package com.raven.service.asymmetrical;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public interface IAsymmetricService {
    void generateKey(int length) throws Exception;
    String encrypt(String text) throws Exception;
    String decrypt(String encrypted) throws Exception;
    void encryptFile(String srcFile, String destFile) throws Exception;
    void decryptFile(String srcFile, String destFile) throws Exception;
    String exportPublicKey();
    String exportPrivateKey();
    PublicKey importPublicKey(String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException;
    PrivateKey importPrivateKey(String privateKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException;
    void setTransformation(String transformation);
}
