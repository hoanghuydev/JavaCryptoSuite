package com.raven.service.symmetrical.implement;

import com.raven.service.symmetrical.ISymmetricCipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

public class BlowfishService implements ISymmetricCipher {
    private SecretKey key;
    private String transformation;

    @Override
    public SecretKey generateSecretKey(int length) throws Exception {
        KeyGenerator key_generator = KeyGenerator.getInstance("Blowfish");
        key_generator.init(length); // Độ dài của khóa từ 32 đến 448 bit
        key = key_generator.generateKey();
        return key;
    }

    @Override
    public String encryptToBase64(String text) throws Exception {
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
            File file = new File(srcFile);
            if (file.isFile()) {
                Cipher cipher = Cipher.getInstance(transformation);

                if (transformation.contains("ECB")) cipher.init(Cipher.ENCRYPT_MODE, key);
                else cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[8]));

                fis = new FileInputStream(file);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int bytes_read;

                while ((bytes_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, bytes_read);
                    if (output_byte != null) fos.write(output_byte);
                }

                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) fos.write(output_byte);

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
        else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

        byte[] encrypted_text_bytes = Base64.getDecoder().decode(text);
        byte[] decrypted_text_bytes = cipher.doFinal(encrypted_text_bytes);
        return new String(decrypted_text_bytes,"UTF-8");
    }

    @Override
    public void decryptFile(String srcFile, String destFile) throws Exception {

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {

            File file = new File(srcFile);
            if (file.isFile()) {

                Cipher cipher = Cipher.getInstance(transformation);

                if (transformation.contains("ECB")) cipher.init(Cipher.DECRYPT_MODE, key);
                else cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[8]));

                fis = new FileInputStream(file);
                fos = new FileOutputStream(destFile);

                byte[] input_byte = new byte[1024];
                int byte_read;

                while ((byte_read = fis.read(input_byte)) != -1) {

                    byte[] output_byte = cipher.update(input_byte, 0, byte_read);
                    if (output_byte != null) fos.write(output_byte);
                }

                byte[] output_byte = cipher.doFinal();
                if (output_byte != null) fos.write(output_byte);

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
            byte[] key_bytes = Base64.getDecoder().decode(keyText.getBytes());
            key = new SecretKeySpec(key_bytes, "Blowfish");
            return key;
        } catch (Exception e) {
            throw new Exception("Failed to import key: " + e.getMessage());
        }
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }
    public static void main(String[] args) throws Exception {
        String plain_text = "Thử mã hoá Blowfish";
        BlowfishService blowFish = new BlowfishService();
        blowFish.setTransformation("Blowfish/CBC/PKCS5Padding");
        blowFish.generateSecretKey(128);
        String encrypted_text = blowFish.encryptToBase64(plain_text);
        String decrypted_text = blowFish.decryptFromBase64(encrypted_text);
        System.out.println("Key: " + blowFish.exportKey());
        System.out.println("Encrypted : " + encrypted_text);
        System.out.println("Decrypted : " + decrypted_text);
        String srcFileEncrypt = "E:\\Dowload\\testMaHoa.json";
        String destFileEncrypt = "E:\\Dowload\\testDaMaHoa.json";
        String destFileDecrypt = "E:\\Dowload\\testDaGiai.json";
        blowFish.encryptFile(srcFileEncrypt, destFileEncrypt);
        blowFish.decryptFile(destFileEncrypt, destFileDecrypt);
    }
}
