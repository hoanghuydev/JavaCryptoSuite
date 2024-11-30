package com.raven.controller.implement;

import com.raven.constant.CipherTypes;
import com.raven.controller.ICipherSwitchable;
import com.raven.controller.IFileEncryptable;
import com.raven.controller.IKeyExportable;
import com.raven.controller.IKeyImportable;
import com.raven.service.signature.SignService;
import lombok.Data;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.io.File;

@Data
public class SignController implements IKeyGeneratable, IKeyExportable, IKeyImportable, IFileEncryptable {
    private SignService signService;
    private JTextArea privateKeyArea;
    private JTextArea publicKeyArea;
    private JLabel filePathArea;
    private JTextArea signatureArea;
    private File file;
    public SignController() {
        try {
            signService = new SignService();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    public void sign() {
        try {
            String signature = signService.signFile(file.getAbsolutePath());
            signatureArea.setText(signature);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    public void verify() {
        try {
            String signature = signatureArea.getText();
            boolean isVerify = signService.verifySignature(file.getAbsolutePath(),signature);
            JOptionPane.showMessageDialog(null, isVerify ? "Signature verified" : "Signature not verified");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    @Override
    public JTextArea getPrivateKeyArea() {
        return privateKeyArea;
    }

    @Override
    public JTextArea getPublicKeyArea() {
        return publicKeyArea;
    }



    @Override
    public void generateKey(int keySize) {
        signService.generateKeyPair();
        String publicKey = signService.getPublicKey();
        publicKeyArea.setText(publicKey);
        String privateKey = signService.getPrivateKey();
        privateKeyArea.setText(privateKey);
    }

    @Override
    public void encryptFile(File file) {

    }

    @Override
    public void decryptFile(File file) {

    }

    @Override
    public void setInputFile(File file) {
       this.file = file;
    }

    @Override
    public void chooseFile() {
        IFileEncryptable.super.chooseFile();
        if (file != null) {
            this.file = file;
            filePathArea.setText(file.getAbsolutePath());
        }
    }
}
