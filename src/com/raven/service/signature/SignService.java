package com.raven.service.signature;

import com.raven.constant.CipherAlgorithms;
import com.raven.constant.PaddingSchemes;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import java.util.Base64;
import java.nio.file.*;

public class SignService {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private KeyPairGenerator keyPairGenerator;
    private Signature signature;

    static {
        // Add BouncyCastle provider
        Security.addProvider(new BouncyCastleProvider());
    }

    public SignService() throws NoSuchAlgorithmException, NoSuchProviderException {
        keyPairGenerator = KeyPairGenerator.getInstance(CipherAlgorithms.RSA, "BC");
        signature = Signature.getInstance(PaddingSchemes.SHA256withRSA, "BC");
    }

    // Generate a new key pair
    public void generateKeyPair() {
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // Get the private key as a Base64 encoded string
    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Get the public key as a Base64 encoded string
    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Sign a file
    public String signFile(String filePath) throws Exception {
        signature.initSign(privateKey);

        // Read the file to sign
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));

        signature.update(fileData);
        byte[] signedData = signature.sign();

        return Base64.getEncoder().encodeToString(signedData);
    }

    // Import private key from Base64
    public void importPrivateKey(String privateKeyBase64) throws GeneralSecurityException {
        byte[] decodedKey = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // You can change to "DSA" or "EC" if needed
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }

    // Import public key from Base64
    public void importPublicKey(String publicKeyBase64) throws GeneralSecurityException {
        byte[] decodedKey = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // You can change to "DSA" or "EC" if needed
        this.publicKey = keyFactory.generatePublic(keySpec);
    }

    // Verify the signature of a file
    public boolean verifySignature(String filePath, String signatureStr) throws Exception {
        signature.initVerify(publicKey);

        // Read the file to verify
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));
        signature.update(fileData);

        byte[] signedData = Base64.getDecoder().decode(signatureStr);

        return signature.verify(signedData);
    }

    public static void main(String[] args) {
        // Test supported algorithms
        String[] algorithms = {"RSA"};

        for (String algorithm : algorithms) {
            try {
                SignService signService = new SignService();

                // Generate key pair
                signService.generateKeyPair();

                // Get private and public keys
                String privateKeyBase64 = signService.getPrivateKey();
                String publicKeyBase64 = signService.getPublicKey();

                System.out.println("Private Key: " + privateKeyBase64);
                System.out.println("Public Key: " + publicKeyBase64);

                // Dummy file test: ensure "E:\\Dowload\\testMaHoa.json" exists
                String signedData = signService.signFile("E:\\Dowload\\testMaHoa.json");
                System.out.println("Signed Data: " + signedData);

                // Import keys from Base64
                signService.importPrivateKey(privateKeyBase64);
                signService.importPublicKey(publicKeyBase64);

                // Verify the signature
                boolean isValid = signService.verifySignature("E:\\Dowload\\testMaHoa.json", signedData);
                System.out.println("Signature verification: " + isValid);

                System.out.println("-----------------------------------------------------\n");

            } catch (Exception e) {
                System.out.println("Error with algorithm: " + algorithm);
                e.printStackTrace();
            }
        }
    }
}
