package com.raven.service.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAService {
    public String hash(String input) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        sha256.update(input.getBytes());
        byte[] digest = sha256.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    public boolean check(String input, String expectedHash) throws NoSuchAlgorithmException {
        String inputHash = hash(input);
        return inputHash.equalsIgnoreCase(expectedHash);
    }

    public static void main(String[] args) {
        try {
            SHAService shaService = new SHAService();
            String input = "mySecurePassword";
            String hashedValue = shaService.hash(input);
            System.out.println("SHA-256 Hash of \"" + input + "\": " + hashedValue);
            boolean isMatch = shaService.check(input, hashedValue);
            System.out.println("Does the input match the hash? " + isMatch);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
