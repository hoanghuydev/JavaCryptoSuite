package com.raven.service.classical.implement;

import com.raven.service.classical.IClassicalService;

import java.util.Random;

public class PolyalphabeticService implements IClassicalService {
    private String key;


    private static final String ALPHABET_VIETNAM =
            "aáàảãạâấầẩẫậbcdđeéèẻẽẹêếềểễệfghiíìỉĩịjklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvwxyýỳỷỹỵz";

    @Override
    public boolean requireKey() {
        return true;
    }

    @Override
    public String generateKey(int length) {
        StringBuilder key = new StringBuilder(length);
        Random random = new Random();


        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHABET_VIETNAM.length());
            key.append(ALPHABET_VIETNAM.charAt(randomIndex));
        }
        this.key = key.toString();
        return this.key;
    }

    @Override
    public String encrypt(String inputText) {
        StringBuilder encryptedText = new StringBuilder();
        int keyLength = key.length();
        int keyIndex = 0;


        for (char c : inputText.toCharArray()) {
            int charIndex = ALPHABET_VIETNAM.indexOf(c);

            if (charIndex != -1) {
                int shift = ALPHABET_VIETNAM.indexOf(Character.toLowerCase(key.charAt(keyIndex % keyLength))) % ALPHABET_VIETNAM.length();

                char encryptedChar = ALPHABET_VIETNAM.charAt((charIndex + shift) % ALPHABET_VIETNAM.length());
                encryptedText.append(encryptedChar);
                keyIndex++;
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    @Override
    public String decrypt(String inputText) {
        StringBuilder decryptedText = new StringBuilder();
        int keyLength = key.length();
        int keyIndex = 0;


        for (char c : inputText.toCharArray()) {
            int charIndex = ALPHABET_VIETNAM.indexOf(c);

            if (charIndex != -1) {
                int shift = ALPHABET_VIETNAM.indexOf(Character.toLowerCase(key.charAt(keyIndex % keyLength))) % ALPHABET_VIETNAM.length();

                char decryptedChar = ALPHABET_VIETNAM.charAt((charIndex - shift + ALPHABET_VIETNAM.length()) % ALPHABET_VIETNAM.length());
                decryptedText.append(decryptedChar);
                keyIndex++;
            } else {
                decryptedText.append(c);
            }
        }
        return decryptedText.toString();
    }


    public static void main(String[] args) {
        PolyalphabeticService polyService = new PolyalphabeticService();


        String originalText = "Chào bạn, tôi tên là Trần Võ Hoàng Huy lớp ĐH21ĐTB!";
        System.out.println("Văn bản gốc: " + originalText);

        String key = polyService.generateKey(16);
        System.out.println("Khóa: " + key);


        String encryptedText = polyService.encrypt(originalText);
        System.out.println("Văn bản sau khi mã hóa: " + encryptedText);


        String decryptedText = polyService.decrypt(encryptedText);
        System.out.println("Văn bản sau khi giải mã: " + decryptedText);
    }
}
