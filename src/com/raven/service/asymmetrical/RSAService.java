package com.raven.service.asymmetrical;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAService {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    // Tạo cặp khóa RSA (public và private)
    public void generateKey(int length) throws Exception {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(length);  // 2048 bits là tiêu chuẩn
        keyPair = keyGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    // Mã hóa chuỗi và chuyển sang Base64
    public String encryptToBase64(String text, String transformation) throws Exception {
        if (publicKey == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Giải mã từ Base64
    public String decryptFromBase64(String encrypted, String transformation) throws Exception {
        if (privateKey == null) return "";
        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    // Mã hóa tập tin với khóa công khai
    public void encryptFile(String srcFile, String destFile, String transformation) throws Exception {
        if (publicKey == null) throw new Exception("Public key is missing.");

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (FileInputStream fis = new FileInputStream(srcFile);
             FileOutputStream fos = new FileOutputStream(destFile);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
            cos.flush();
            System.out.println("File encrypted successfully: " + destFile);
        }
    }

    // Giải mã tập tin với khóa riêng
    public void decryptFile(String srcFile, String destFile, String transformation) throws Exception {
        if (privateKey == null) throw new Exception("Private key is missing.");

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (FileInputStream fis = new FileInputStream(srcFile);
             FileOutputStream fos = new FileOutputStream(destFile);
             CipherInputStream cis = new CipherInputStream(fis, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
            System.out.println("File decrypted successfully: " + destFile);
        }
    }

    // Xuất khóa công khai dưới dạng Base64
    public String exportPublicKey() {
        if (publicKey == null) return null;
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Xuất khóa riêng dưới dạng Base64
    public String exportPrivateKey() {
        if (privateKey == null) return null;
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Nhập khóa công khai từ chuỗi Base64
    public PublicKey importPublicKey(String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bytePublicKey = Base64.getDecoder().decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytePublicKey);
        publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    // Nhập khóa riêng từ chuỗi Base64
    public PrivateKey importPrivateKey(String privateKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bytePrivateKey = Base64.getDecoder().decode(privateKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
        privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    public static void main(String[] args) {
        try {
            // Tạo đối tượng RSAService và tạo cặp khóa
            RSAService rsaService = new RSAService();
            rsaService.generateKey(2048);

            // Mã hóa và giải mã chuỗi
            String text = "Thử mã hóa RSA!";
            System.out.println("Original text: " + text);
            String encryptedTextBase64 = rsaService.encryptToBase64(text, "RSA");
            System.out.println("Encrypted text (Base64): " + encryptedTextBase64);

            String decryptedText = rsaService.decryptFromBase64(encryptedTextBase64, "RSA");
            System.out.println("Decrypted text: " + decryptedText);

            // Mã hóa và giải mã tập tin
            String srcFileEncrypt = "E:\\Dowload\\testMaHoa.json";
            String destFileEncrypt = "E:\\Dowload\\testDaMaHoa.json";
            String destFileDecrypt = "E:\\Dowload\\testDaGiai.json";
            // Mã hóa tập tin
            rsaService.encryptFile(srcFileEncrypt, destFileEncrypt, "RSA");

            // Giải mã tập tin
            rsaService.decryptFile(destFileEncrypt, destFileDecrypt, "RSA");

            // Kiểm tra nội dung file giải mã
            String decryptedFileContent = new String(Files.readAllBytes(Paths.get(destFileDecrypt)));
            System.out.println("Decrypted file content: " + decryptedFileContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
