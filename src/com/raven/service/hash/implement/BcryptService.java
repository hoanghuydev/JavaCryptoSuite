package com.raven.service.hash.implement;

import com.raven.service.hash.IHashService;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptService implements IHashService {

    @Override
    public String hash(String plainText) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(plainText, salt);
    }
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    public static void main(String[] args) {
        BcryptService bcryptService = new BcryptService();
        String plainPassword = "mySecurePassword";
        String hashedPassword = bcryptService.hash(plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        boolean isPasswordCorrect = bcryptService.verifyPassword(plainPassword, hashedPassword);
        System.out.println("Password Verified: " + isPasswordCorrect);
        boolean isIncorrectPassword = bcryptService.verifyPassword("wrongPassword", hashedPassword);
        System.out.println("Incorrect Password Verified: " + isIncorrectPassword);
    }
}
