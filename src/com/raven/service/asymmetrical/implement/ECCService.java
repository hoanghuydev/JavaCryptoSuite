package com.raven.service.asymmetrical.implement;

import com.raven.service.asymmetrical.IAsymmetricService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ECCService implements IAsymmetricService {
    private KeyPair keyPair;
    private ECPublicKey publicKey;
    private ECPrivateKey privateKey;
    private String transformation;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void generateKey(int length) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        keyPair = keyPairGenerator.generateKeyPair();
        publicKey = (ECPublicKey) keyPair.getPublic();
        privateKey = (ECPrivateKey) keyPair.getPrivate();
    }

    @Override
    public String encrypt(String text) throws Exception {
        if (publicKey == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encrypted) throws Exception {
        if (privateKey == null) return "";
        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    // Mã hóa tập tin với khóa công khai
    @Override
    public void encryptFile(String srcFile, String destFile) throws Exception {
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
    @Override
    public void decryptFile(String srcFile, String destFile) throws Exception {
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
    @Override
    public String exportPublicKey() {
        if (publicKey == null) return null;
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Xuất khóa riêng dưới dạng Base64
    @Override
    public String exportPrivateKey() {
        if (privateKey == null) return null;
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Nhập khóa công khai từ chuỗi Base64
    @Override
    public PublicKey importPublicKey(String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        byte[] bytePublicKey = Base64.getDecoder().decode(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytePublicKey);
        publicKey = (ECPublicKey) keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    // Nhập khóa riêng từ chuỗi Base64
    @Override
    public PrivateKey importPrivateKey(String privateKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        byte[] bytePrivateKey = Base64.getDecoder().decode(privateKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
        privateKey = (ECPrivateKey) keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    @Override
    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public static void main(String[] args) {
        try {
            // Tạo đối tượng ECCService và tạo cặp khóa
            ECCService eccService = new ECCService();
            eccService.generateKey(256); // SECP256r1 (256 bits)

            // Mã hóa và giải mã chuỗi
            String text = "Thử mã hóa ECC!";
            System.out.println("Original text: " + text);
            String encryptedTextBase64 = eccService.encrypt(text);
            System.out.println("Encrypted text (Base64): " + encryptedTextBase64);

            String decryptedText = eccService.decrypt(encryptedTextBase64);
            System.out.println("Decrypted text: " + decryptedText);

            // Mã hóa và giải mã tập tin
            String srcFileEncrypt = "E:\\Dowload\\testMaHoa.json";
            String destFileEncrypt = "E:\\Dowload\\testDaMaHoa.json";
            String destFileDecrypt = "E:\\Dowload\\testDaGiai.json";

            // Mã hóa tập tin
            eccService.encryptFile(srcFileEncrypt, destFileEncrypt);

            // Giải mã tập tin
            eccService.decryptFile(destFileEncrypt, destFileDecrypt);

            // Kiểm tra nội dung file giải mã
            String decryptedFileContent = new String(Files.readAllBytes(Paths.get(destFileDecrypt)));
            System.out.println("Decrypted file content: " + decryptedFileContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
