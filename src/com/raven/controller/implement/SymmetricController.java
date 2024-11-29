package com.raven.controller.implement;

import com.raven.constant.Constants;
import com.raven.controller.ICipherSwitchable;
import com.raven.controller.IFileEncryptable;
import com.raven.controller.IModeSwitchable;
import com.raven.controller.ITextEncryptable;
import com.raven.service.symmetrical.ISymmetricService;
import com.raven.service.symmetrical.implement.*;
import lombok.Data;

import javax.swing.*;
import java.io.File;

@Data
public class SymmetricController implements IModeSwitchable, ITextEncryptable, IKeyGeneratable, IFileEncryptable, ICipherSwitchable<ISymmetricService> {
    private boolean isEncryptMode = true;
    private File file;
    private ISymmetricService symmetricService;
    private JComboBox<String> algorithmCombo;
    private JComboBox<String> modeCombo;
    private JComboBox<String> keySizeCombo;
    private JButton generateKeyButton;
    private JButton executeButton;
    private JButton  resetButton;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JButton copyKeyButton;
    private JLabel messageFileExecute;
    private JLabel filePathLabel;
    private Constants.Tab currentTab = Constants.Tab.FILE;
    private JTextArea keyTextArea;

    public SymmetricController(){
        symmetricService = new AESService();
    }
    @Override
    public boolean isEncryptMode() {
        return isEncryptMode;
    }

    @Override
    public void setIsEncryptMode(boolean isEncrypt) {
        isEncryptMode = isEncrypt;
    }

    @Override
    public void generateKey(int keySize) {
        try {
            symmetricService.generateSecretKey(keySize);
            String key = symmetricService.exportKey();
            keyTextArea.setText(key);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    public void resetForm() {
        keyTextArea.setText("");
        inputTextArea.setText("");
        outputTextArea.setText("");
        messageFileExecute.setText("👉 Result will appear here!");
        filePathLabel.setText("No file selected");
        file= null;
    }

    public void execute(String transformation) throws Exception {
        symmetricService.importKey(keyTextArea.getText());
        symmetricService.setTransformation(transformation);
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
                String encryptText = symmetricService.encrypt(inputTextArea.getText());
                outputTextArea.setText(encryptText);
            } else {
                String decryptText = symmetricService.decrypt(inputTextArea.getText());
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
    public ISymmetricService getCipher() {
        return symmetricService;
    }

    @Override
    public void setCipher(ISymmetricService cipher) {
        symmetricService = cipher;

    }

    @Override
    public void encryptFile(File file) {

        try {
            String newFileName = getFileWithSuffix(file, "_encrypted");
            symmetricService.encryptFile(file.getAbsolutePath(), newFileName);
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
            symmetricService.decryptFile(file.getAbsolutePath(), newFileName);
            messageFileExecute.setText("File decrypted: " + newFileName);
            filePathLabel.setText("Decrypted file saved as: " + newFileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Decryption failed: " + e.getMessage());
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
    public void chooseFile() {
        IFileEncryptable.super.chooseFile();
        if (file != null) {
            filePathLabel.setText(file.getAbsolutePath());
        }
    }

    @Override
    public void setInputFile(File file) {
        this.file = file;

    }
}
