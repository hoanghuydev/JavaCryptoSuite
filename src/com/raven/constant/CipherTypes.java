package com.raven.constant;

public class CipherTypes {
    public static final String[] SYMMETRIC_CIPHERS = {
            CipherAlgorithms.AES, CipherAlgorithms.DES, CipherAlgorithms.BLOWFISH, CipherAlgorithms.SERPENT, CipherAlgorithms.TWOFISH
    };

    public static final String[] ASYMMETRIC_CIPHERS = {
            CipherAlgorithms.RSA, CipherAlgorithms.ECC
    };

    public static final String[] BASIC_CIPHERS = {
            CipherAlgorithms.BASE64, CipherAlgorithms.CAESAR, CipherAlgorithms.SUBSTITUTION, CipherAlgorithms.POLYALPHA
    };

    public static final String[] HASH_CIPHERS = {
            CipherAlgorithms.BCRYPT, CipherAlgorithms.MD5, CipherAlgorithms.SHA256
    };
}
