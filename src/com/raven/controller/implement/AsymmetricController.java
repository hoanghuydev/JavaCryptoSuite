package com.raven.controller.implement;

import com.raven.constant.Constants;

import com.raven.controller.*;
import com.raven.service.asymmetrical.IAsymmetricService;
import com.raven.service.asymmetrical.implement.ECCService;
import com.raven.service.asymmetrical.implement.RSAService;
import lombok.Data;

import javax.swing.*;
import java.io.File;
@Data
public class AsymmetricController implements IModeSwitchable, IKeyImportable, IKeyExportable, IFileEncryptable, ITextEncryptable, IKeyGeneratable, ICipherSwitchable<IAsymmetricService> {
    private boolean isEncryptMode = true;
    private JLabel messageFileExecute;
    private JTextArea publicKeyArea;
    private JTextArea privateKeyArea;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private File file;
    private JLabel filePathLabel;
    private Constants.Tab currentTab = Constants.Tab.FILE;
    private IAsymmetricService asymmetricService;

    public AsymmetricController() {
        asymmetricService = new RSAService();
    }

    public void resetForm() {
        if (publicKeyArea != null) publicKeyArea.setText("");
        if (privateKeyArea != null) privateKeyArea.setText("");
        if (messageFileExecute != null) messageFileExecute.setText("");
        if (inputTextArea != null) inputTextArea.setText("");
        if (outputTextArea != null) outputTextArea.setText("");
        setIsEncryptMode(true);
    }

    @Override
    public void generateKey(int keySize)  {
        try {
            asymmetricService.generateKey(keySize);
            String publicKey = asymmetricService.exportPublicKey();
            publicKeyArea.setText(publicKey);
            String privateKey = asymmetricService.exportPrivateKey();
            privateKeyArea.setText(privateKey);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void execute(String transformation) throws Exception {
        asymmetricService.importPrivateKey(privateKeyArea.getText());
        asymmetricService.importPublicKey(publicKeyArea.getText());
        asymmetricService.setTransformation(transformation);
        if (currentTab== Constants.Tab.FILE) {
            if (file == null) {
                JOptionPane.showMessageDialog(null, "No file selected.");
                return;
            }
            if (isEncryptMode) {
                encryptFile(file);
            }
            else {
                decryptFile(file);
            }
        } else {
            if (isEncryptMode) {
                String encryptText = asymmetricService.encrypt(inputTextArea.getText());
                outputTextArea.setText(encryptText);
            } else {
                String decryptText = asymmetricService.decrypt(inputTextArea.getText());
                outputTextArea.setText(decryptText);
            }
        }

    }
    @Override
    public void encrypt(String plainText) {

    }

    @Override
    public void decrypt(String cipherText) {

    }

    @Override
    public JTextArea getPublicKeyArea() {
        return publicKeyArea;
    }

    @Override
    public JTextArea getPrivateKeyArea() {
        return privateKeyArea;
    }

    @Override
    public boolean isEncryptMode() {
        return isEncryptMode;
    }

    @Override
    public void setIsEncryptMode(boolean isEncrypt) {
        this.isEncryptMode = isEncrypt;
    }

    @Override
    public void chooseFile() {
        IFileEncryptable.super.chooseFile();
        if (file != null) {
            filePathLabel.setText(file.getAbsolutePath());
        }
    }
    @Override
    public void encryptFile(File file) {

        try {
            String newFileName = getFileWithSuffix(file, "_encrypted");
            asymmetricService.encryptFile(file.getAbsolutePath(), newFileName);
            messageFileExecute.setText("File encrypted: " + newFileName);
            filePathLabel.setText("Encrypted file saved as: " + newFileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Encryption failed: " + e.getMessage());
        }
    }

    @Override
    public void decryptFile(File file) {

        try {
            String newFileName = getFileWithSuffix(file, "_decrypted");
            asymmetricService.decryptFile(file.getAbsolutePath(), newFileName);
            messageFileExecute.setText("File decrypted: " + newFileName);
            filePathLabel.setText("Decrypted file saved as: " + newFileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Decryption failed: " + e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    private String getFileWithSuffix(File file, String suffix) {
        String filePath = file.getAbsolutePath();
        String fileName = file.getName();
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex);
            fileName = fileName.substring(0, dotIndex);
        }
        return file.getParent() + File.separator + fileName + suffix + fileExtension;
    }

    @Override
    public void setInputFile(File file) {
        this.file = file;
    }

    @Override
    public IAsymmetricService getCipher() {
        return null;
    }

    @Override
    public void setCipher(IAsymmetricService cipher) {
        asymmetricService = cipher;

    }
}
