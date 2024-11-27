package com.raven.service.hash.implement;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptService {
    public String hashPassword(String plainPassword) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(plainPassword, salt);
    }
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    public static void main(String[] args) {
        BcryptService bcryptService = new BcryptService();
        String plainPassword = "mySecurePassword";
        String hashedPassword = bcryptService.hashPassword(plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        boolean isPasswordCorrect = bcryptService.verifyPassword(plainPassword, hashedPassword);
        System.out.println("Password Verified: " + isPasswordCorrect);
        boolean isIncorrectPassword = bcryptService.verifyPassword("wrongPassword", hashedPassword);
        System.out.println("Incorrect Password Verified: " + isIncorrectPassword);
    }
}
