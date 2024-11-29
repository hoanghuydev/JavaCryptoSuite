package com.raven.service.symmetrical.implement;

import com.raven.service.symmetrical.ISymmetricService;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public class DESService implements ISymmetricService {
    private SecretKey key;
    private String transformation;

    @Override
    public SecretKey generateSecretKey(int length) throws Exception {
        KeyGenerator key_generator = KeyGenerator.getInstance("DES");
        key_generator.init(length);
        key = key_generator.generateKey();
        return key;
    }

    @Override
    public String encrypt(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
        else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

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
                else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));
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
    public String decrypt(String text) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(transformation);

        if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        var plain_text = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(plain_text, "UTF-8");
    }

    @Override
    public void decryptFile(String srcFile, String destFile) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            if (key == null) throw new Exception("Không tìm thấy khóa");
            File fileSrc = new File(srcFile);
            if (fileSrc.isFile()) {
                Cipher cipher = Cipher.getInstance(transformation);
                if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
                else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));
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
            SecretKey importedKey = new SecretKeySpec(keyBytes, "DES");
            this.key = importedKey;
            return importedKey;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public static void main(String[] args) throws Exception {
        String plain_text = "Thử code mã hóa DES";
        DESService des = new DESService();
        des.setTransformation("DES/CBC/PKCS5Padding");
        des.generateSecretKey(56);
        String encrypt_text = des.encrypt(plain_text);
        System.out.println("Key: " + des.exportKey());
        System.out.println("Encrypt To Base64: " + encrypt_text);
        System.out.println(des.decrypt(encrypt_text));
        String srcFileEncrypt = "E:\\Dowload\\testMaHoa.json";
        String destFileEncrypt = "E:\\Dowload\\testDaMaHoa.json";
        String destFileDecrypt = "E:\\Dowload\\testDaGiai.json";
        des.encryptFile(srcFileEncrypt, destFileEncrypt);
        des.decryptFile(destFileEncrypt, destFileDecrypt);
    }
}
