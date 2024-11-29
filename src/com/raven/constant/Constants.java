package com.raven.constant;

import lombok.Data;

public class Constants {
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

    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String BLOWFISH = "Blowfish";
    public static final String SERPENT = "Serpent";
    public static final String TWOFISH = "Twofish";
    public static final String RSA = "RSA";
    public static final String ECC = "ECC";
    public static final String BCRYPT = "Bcrypt";
    public static final String DSA = "DSA";
    public static final String PKCS5Padding = "PKCS5Padding";
    public static final String PKCS7Padding = "PKCS7Padding";
    public static final String NoPadding = "NoPadding";
    public static final String ISO10126Padding = "ISO10126Padding";
    public static final String PKCS1Padding = "PKCS1Padding";
    public static final String OAEPWithSHA1AndMGF1Padding = "OAEPWithSHA1AndMGF1Padding";
    public static final String OAEPWithSHA256AndMGF1Padding = "OAEPWithSHA256AndMGF1Padding";
    public static final String ECDSA = "ECDSA";
    public static final String ECDH = "ECDH";

    public static final int[] AES_KEY_LENGTHS = {128, 192, 256};  // AES key sizes in bits
    public static final int[] DES_KEY_LENGTHS = {56};  // DES uses 56-bit key
    public static final int[] BLOWFISH_KEY_LENGTHS = {32, 64, 128};  // Blowfish key sizes
    public static final int[] SERPENT_KEY_LENGTHS = {128, 192, 256};  // Serpent key sizes
    public static final int[] TWOFISH_KEY_LENGTHS = {128, 192, 256};  // Twofish key sizes
    public static final int[] RSA_KEY_LENGTHS = {512, 1024, 2048, 4096};  // RSA key sizes
    public static final int[] ECC_KEY_LENGTHS = {160, 224, 256, 384, 521};  // ECC key sizes

    public static final String[] symmetricCiphers = {
            AES,
            DES,
            BLOWFISH,
            SERPENT,
            TWOFISH
    };

    public static final String[] asymmetricCiphers = {
            RSA,
            ECC
    };

}
