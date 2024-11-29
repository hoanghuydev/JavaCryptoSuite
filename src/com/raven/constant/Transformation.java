package com.raven.constant;

import java.util.*;

public class Transformation {

    private static final Map<String, List<String>> cipherModes = new HashMap<>();
    private static final Map<String, List<String>> paddingSchemes = new HashMap<>();

    static {
        cipherModes.put(Constants.AES, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(Constants.DES, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(Constants.BLOWFISH, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(Constants.SERPENT, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(Constants.TWOFISH, Arrays.asList("ECB", "CBC", "CFB", "OFB"));
        cipherModes.put(Constants.RSA, Arrays.asList("ECB"));  // RSA thường chỉ có chế độ ECB
        cipherModes.put(Constants.ECC, Arrays.asList("ECDSA", "ECDH"));

        paddingSchemes.put(Constants.AES, Arrays.asList(Constants.PKCS5Padding, Constants.PKCS7Padding, Constants.NoPadding, Constants.ISO10126Padding));
        paddingSchemes.put(Constants.DES, Arrays.asList(Constants.PKCS5Padding, Constants.NoPadding));
        paddingSchemes.put(Constants.BLOWFISH, Arrays.asList(Constants.PKCS5Padding, Constants.PKCS7Padding, Constants.NoPadding));
        paddingSchemes.put(Constants.SERPENT, Arrays.asList(Constants.PKCS5Padding, Constants.PKCS7Padding, Constants.NoPadding));
        paddingSchemes.put(Constants.TWOFISH, Arrays.asList(Constants.PKCS5Padding, Constants.PKCS7Padding, Constants.NoPadding));
        paddingSchemes.put(Constants.RSA, Arrays.asList(Constants.PKCS1Padding, Constants.OAEPWithSHA1AndMGF1Padding, Constants.OAEPWithSHA256AndMGF1Padding));
        paddingSchemes.put(Constants.ECC, Arrays.asList(Constants.ECDSA, Constants.ECDH));
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
