package com.raven.constant;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KeyLengths {
    public static final int[] AES_KEY_LENGTHS = {128, 192, 256};
    public static final int[] DES_KEY_LENGTHS = {56};
    public static final int[] BLOWFISH_KEY_LENGTHS = {32, 64, 128};
    public static final int[] SERPENT_KEY_LENGTHS = {128, 192, 256};
    public static final int[] TWOFISH_KEY_LENGTHS = {128, 192, 256};
    public static final int[] RSA_KEY_LENGTHS = {2048, 4096};
    public static final int[] ECC_KEY_LENGTHS = { 256, 384, 521};
    public static final Map<String, int[]> ALGORITHM_KEY_LENGTHS = Map.of(
            CipherAlgorithms.AES, AES_KEY_LENGTHS,
            CipherAlgorithms.DES, DES_KEY_LENGTHS,
            CipherAlgorithms.BLOWFISH, BLOWFISH_KEY_LENGTHS,
            CipherAlgorithms.SERPENT, SERPENT_KEY_LENGTHS,
            CipherAlgorithms.TWOFISH, TWOFISH_KEY_LENGTHS,
            CipherAlgorithms.RSA, RSA_KEY_LENGTHS,
            CipherAlgorithms.ECC, ECC_KEY_LENGTHS
    );
    public static int[] getKeyLengths(String algorithm) {
        return ALGORITHM_KEY_LENGTHS.getOrDefault(algorithm, new int[0]);
    }
}
