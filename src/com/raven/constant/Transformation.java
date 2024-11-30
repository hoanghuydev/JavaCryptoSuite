package com.raven.constant;

import java.util.*;

public class Transformation {

    private static final Map<String, List<String>> cipherModes = new HashMap<>();
    private static final Map<String, List<String>> paddingSchemes = new HashMap<>();

    static {
        cipherModes.put(CipherAlgorithms.AES, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(CipherAlgorithms.DES, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(CipherAlgorithms.BLOWFISH, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(CipherAlgorithms.SERPENT, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(CipherAlgorithms.TWOFISH, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(CipherAlgorithms.RSA, Arrays.asList("ECB"));  // RSA thường chỉ có chế độ ECB
        cipherModes.put(CipherAlgorithms.EC, Arrays.asList("ECDSA", "ECDH"));

        paddingSchemes.put(CipherAlgorithms.AES, Arrays.asList(PaddingSchemes.PKCS5Padding, PaddingSchemes.PKCS7Padding, PaddingSchemes.NoPadding, PaddingSchemes.ISO10126Padding));
        paddingSchemes.put(CipherAlgorithms.DES, Arrays.asList(PaddingSchemes.PKCS5Padding, PaddingSchemes.NoPadding));
        paddingSchemes.put(CipherAlgorithms.BLOWFISH, Arrays.asList(PaddingSchemes.PKCS5Padding, PaddingSchemes.PKCS7Padding, PaddingSchemes.NoPadding));
        paddingSchemes.put(CipherAlgorithms.SERPENT, Arrays.asList(PaddingSchemes.PKCS5Padding, PaddingSchemes.PKCS7Padding, PaddingSchemes.NoPadding));
        paddingSchemes.put(CipherAlgorithms.TWOFISH, Arrays.asList(PaddingSchemes.PKCS5Padding, PaddingSchemes.PKCS7Padding, PaddingSchemes.NoPadding));
        paddingSchemes.put(CipherAlgorithms.RSA, Arrays.asList(PaddingSchemes.PKCS1Padding, PaddingSchemes.OAEPWithSHA1AndMGF1Padding, PaddingSchemes.OAEPWithSHA256AndMGF1Padding));
        paddingSchemes.put(CipherAlgorithms.EC, Arrays.asList(PaddingSchemes.ECDSA, PaddingSchemes.ECDH));
    }

    public static List<String> getTransformations(String algorithm) {
        if (!cipherModes.containsKey(algorithm) || !paddingSchemes.containsKey(algorithm)) {
            throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }

        List<String> modes = cipherModes.get(algorithm);
        List<String> paddings = paddingSchemes.get(algorithm);
        List<String> transformations = new ArrayList<>();
        for (String mode : modes) {
            for (String padding : paddings) {
                transformations.add(algorithm + "/" + mode + "/" + padding);
            }
        }

        return transformations;
    }
}
