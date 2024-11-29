package com.raven.service.classical.implement;

import com.raven.service.classical.IClassicalService;

import java.util.Base64;

public class Base64Service implements IClassicalService {

    @Override
    public boolean requireKey() {
        return false;
    }

    @Override
    public String generateKey(int length) {
        return "";
    }

    @Override
    public String encrypt(String inputText) {
        return Base64.getEncoder().encodeToString(inputText.getBytes());
    }

    @Override
    public String decrypt(String inputText) {
        byte[] decodedBytes = Base64.getDecoder().decode(inputText);
        return new String(decodedBytes);
    }
    public static void main(String[] args) {
        Base64Service base64Service = new Base64Service();
        String originalText = "Chào bạn, tôi tên là Trần Võ Hoàng Huy lớp ĐH21ĐTB!";
        System.out.println("Văn bản gốc: " + originalText);
        String encryptedText = base64Service.encrypt(originalText);
        System.out.println("Văn bản sau khi mã hóa (Base64): " + encryptedText);
        String decryptedText = base64Service.decrypt(encryptedText);
        System.out.println("Văn bản sau khi giải mã: " + decryptedText);
    }
}
