package com.raven.service.asymmetrical;

import java.io.*;
import java.security.*;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DSAService {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    // Generate DSA key pair
    public void generateKey(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(keySize);
        keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    // Export public key as Base64
    public String exportPublicKey() {
        if (publicKey == null) return null;
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Export private key as Base64
    public String exportPrivateKey() {
        if (privateKey == null) return null;
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Import public key from Base64
    public PublicKey importPublicKey(String base64PublicKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Import private key from Base64
    public PrivateKey importPrivateKey(String base64PrivateKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // Sign data using the private key
    public byte[] sign(String data) throws Exception {
        if (privateKey == null) throw new Exception("Private key is missing.");
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return signature.sign();
    }

    // Verify the signature using the public key
    public boolean verify(String data, byte[] signatureBytes) throws Exception {
        if (publicKey == null) throw new Exception("Public key is missing.");
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(signatureBytes);
    }

    // Example for generating keys from parameters using DSAPrivateKeySpec and DSAPublicKeySpec
    public void generateKeysFromParams(String p, String q, String g, String privateKey) throws Exception {
        DSAPrivateKeySpec privateKeySpec = new DSAPrivateKeySpec(new java.math.BigInteger(privateKey), new java.math.BigInteger(p), new java.math.BigInteger(q), new java.math.BigInteger(g));
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);

        DSAPublicKeySpec publicKeySpec = new DSAPublicKeySpec(new java.math.BigInteger(privateKey).modPow(new java.math.BigInteger("2"), new java.math.BigInteger(p)), new java.math.BigInteger(p), new java.math.BigInteger(q), new java.math.BigInteger(g));
        this.publicKey = keyFactory.generatePublic(publicKeySpec);
    }

    // For testing purposes (Main Method)
    public static void main(String[] args) {
        try {
            DSAService dsaService = new DSAService();
            dsaService.generateKey(1024);

            String publicKeyBase64 = dsaService.exportPublicKey();
            System.out.println("Public Key: " + publicKeyBase64);

            String privateKeyBase64 = dsaService.exportPrivateKey();
            System.out.println("Private Key: " + privateKeyBase64);

            // Importing the keys back
            PublicKey importedPublicKey = dsaService.importPublicKey(publicKeyBase64);
            PrivateKey importedPrivateKey = dsaService.importPrivateKey(privateKeyBase64);

            // Signing and verifying data
            String data = "Hello, this is a test message!";
            byte[] signature = dsaService.sign(data);
            boolean isVerified = dsaService.verify(data, signature);
            System.out.println("Signature Verified: " + isVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
