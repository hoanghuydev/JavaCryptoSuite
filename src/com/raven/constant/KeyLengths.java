package com.raven.constant;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KeyLengths {
    public static final Map<String, int[]> ALGORITHM_KEY_LENGTHS = Map.of(
            Constants.AES, Constants.AES_KEY_LENGTHS,
            Constants.DES, Constants.DES_KEY_LENGTHS,
            Constants.BLOWFISH, Constants.BLOWFISH_KEY_LENGTHS,
            Constants.SERPENT, Constants.SERPENT_KEY_LENGTHS,
            Constants.TWOFISH, Constants.TWOFISH_KEY_LENGTHS,
            Constants.RSA, Constants.RSA_KEY_LENGTHS,
            Constants.ECC, Constants.ECC_KEY_LENGTHS
    );
    public static int[] getKeyLengths(String algorithm) {
        return ALGORITHM_KEY_LENGTHS.getOrDefault(algorithm, new int[0]);
    }
}
