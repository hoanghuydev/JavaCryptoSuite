package com.raven.service.hash.implement;

import com.raven.service.hash.IHashService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Service implements IHashService {
    public String hash(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean check(String input, String expectedHash) throws NoSuchAlgorithmException {
        String inputHash = hash(input);
        return inputHash.equalsIgnoreCase(expectedHash);
    }

    public static void main(String[] args) {
        try {
            MD5Service md5Service = new MD5Service();
            String input = "mySecurePassword";
            String hashedValue = md5Service.hash(input);
            System.out.println("MD5 Hash of \"" + input + "\": " + hashedValue);
            boolean isMatch = md5Service.check(input, hashedValue);
            System.out.println("Does the input match the hash? " + isMatch);
            boolean isMatchWrong = md5Service.check("wrongPassword", hashedValue);
            System.out.println("Does the wrong input match the hash? " + isMatchWrong);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
