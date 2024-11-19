package com.raven.service.symmetrical;

import com.raven.service.symmetrical.implement.ISymmetricCipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Security;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class TwofishService implements ISymmetricCipher {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    private SecretKey key;
    private String transformation;

    @Override
    public SecretKey generateSecretKey(int length) throws Exception {
        KeyGenerator key_generator = KeyGenerator.getInstance("TwoFish","BC");
        key_generator.init(length);
        key = key_generator.generateKey();
        return key;
    }

    @Override
    public String encryptToBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);
        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
        var text_bytes = text.getBytes("UTF-8");
        var encrypted_text_bytes = cipher.doFinal(text_bytes);
        return Base64.getEncoder().encodeToString(encrypted_text_bytes);
    }

    @Override
    public void encryptFile(String srcFile, String destFile) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            if (key == null) throw new Exception("Key Not Found");
            File fileSrc = new File(srcFile);
            if (fileSrc.isFile()) {
                Cipher cipher = Cipher.getInstance(transformation);
                if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
                else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
                fis = new FileInputStream(fileSrc);
                fos = new FileOutputStream(destFile);
                byte[] input_byte = new byte[1024];
                int bytes_read;
                while ((bytes_read = fis.read(input_byte)) != -1) {
                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) fos.write(output_byte);
                }
                byte[] output = cipher.doFinal();
                if (output != null) fos.write(output);
                fos.flush();
                System.out.println("Mã hóa file thành công");

            }
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }

    }

    @Override
    public String decryptFromBase64(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);
        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
        var plain_text = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(plain_text, "UTF-8");
    }

    @Override
    public void decryptFile(String srcFile, String destFile) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            if (key == null) throw new Exception("Key Not Found");
            File fileSrc = new File(srcFile);
            if (fileSrc.isFile()) {
                Cipher cipher = Cipher.getInstance(transformation);
                if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
                else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
                fis = new FileInputStream(fileSrc);
                fos = new FileOutputStream(destFile);
                byte[] input_byte = new byte[1024];
                int bytes_read;
                while ((bytes_read = fis.read(input_byte)) != -1) {
                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) fos.write(output_byte);
                }
                byte[] output = cipher.doFinal();
                if (output != null) fos.write(output);
                fos.flush();
                System.out.println("Giải mã file thành công");

            }
        } finally {

            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }

    }

    @Override
    public String exportKey() throws Exception {
        if (key == null) return "";
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    @Override
    public SecretKey importKey(String keyText) throws Exception {

        if (keyText == null || keyText.isEmpty()) {
            throw new IllegalArgumentException("Invalid key text");
        }

        try {
            byte[] keyBytes = Base64.getDecoder().decode(keyText);
            SecretKey importedKey = new SecretKeySpec(keyBytes, "TwoFish");
            this.key = importedKey;
            return importedKey;
        } catch (Exception e) {
            throw new Exception("Failed to import key: " + e.getMessage());
        }
    }

    public SecretKey getKey() {
        return key;
    }
    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public static void main(String[] args) throws Exception {
        String plain_text = "Thử mã hóa Twofish";
        TwofishService twoFish = new TwofishService();
        twoFish.setTransformation("TwoFish/CBC/PKCS5Padding");
        twoFish.generateSecretKey(128);
        String encrypt_text = twoFish.encryptToBase64(plain_text);
        System.out.println("Key: " + twoFish.exportKey());
        System.out.println("Encrypt To Base64: " + encrypt_text);
        System.out.println(twoFish.decryptFromBase64(encrypt_text));
        String srcFileEncrypt = "E:\\Dowload\\testMaHoa.json";
        String destFileEncrypt = "E:\\Dowload\\testDaMaHoa.json";
        String destFileDecrypt = "E:\\Dowload\\testDaGiai.json";
        twoFish.encryptFile(srcFileEncrypt, destFileEncrypt);
        twoFish.decryptFile(destFileEncrypt, destFileDecrypt);
    }
}
