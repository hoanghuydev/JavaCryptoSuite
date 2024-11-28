package com.raven.controller.implement;

import com.raven.constant.Constants;
import com.raven.constant.asymmetric.algorithm.AsymmetricAlgorithm;
import com.raven.constant.asymmetric.key.AsymmetricKeySize;
import com.raven.constant.asymmetric.padding.ECCPadding;
import com.raven.constant.asymmetric.padding.RSAPadding;
import com.raven.controller.IModeSwitchable;
import com.raven.controller.IFileEncryptable;
import com.raven.controller.IKeyExportable;
import com.raven.controller.IKeyImportable;
import com.raven.service.asymmetrical.implement.ECCService;
import com.raven.service.asymmetrical.implement.RSAService;

import javax.swing.*;
import java.io.File;

public class AsymmetricController implements IModeSwitchable, IKeyImportable, IKeyExportable, IFileEncryptable {
    private JTabbedPane tabbedPane;
    private int keySize = Integer.parseInt(String.valueOf(AsymmetricKeySize.SIZE_512.getSize()));
    private AsymmetricAlgorithm algorithm = AsymmetricAlgorithm.RSA;
    private Object padding = RSAPadding.NO_PADDING.getValue();
    private boolean isEncryptMode = true;
    private JLabel messageFileExecute;
    private JTextArea publicKeyArea;
    private JTextArea privateKeyArea;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private File inputFile;
    private JLabel filePathLabel;
    private boolean isEncrypt = true;
    private Constants.TabType currentTab;
    private RSAService rsaService;
    private ECCService eccService;
    public AsymmetricController() {
        this.rsaService = getRsaService();
        this.eccService = getEccService();
    }
    public RSAService getRsaService() {
        if (rsaService == null) {
            rsaService = new RSAService();
        }
        return rsaService;
    }

    public ECCService getEccService() {
        if (eccService == null) {
            eccService = new ECCService();
        }
        return eccService;
    }


    public void resetForm() {
        if (publicKeyArea != null) publicKeyArea.setText("");
        if (privateKeyArea != null) privateKeyArea.setText("");
        if (messageFileExecute != null) messageFileExecute.setText("");
        if (inputTextArea != null) inputTextArea.setText("");
        if (outputTextArea != null) outputTextArea.setText("");
        setIsEncryptMode(true);
    }
    public void genarateKey() throws Exception {
        System.out.println("Gen key");
        if (AsymmetricAlgorithm.RSA.equals(algorithm)) {
            rsaService.generateKey(keySize);
            System.out.println(rsaService.exportPrivateKey());
            publicKeyArea.setText(rsaService.exportPublicKey());
            privateKeyArea.setText(rsaService.exportPrivateKey());
        } else if (AsymmetricAlgorithm.ECC.equals(algorithm)) {
            eccService.generateKey(keySize);
            publicKeyArea.setText(eccService.exportPublicKey());
            privateKeyArea.setText(eccService.exportPrivateKey());
        }
    }

    public void execute() throws Exception {
        if (currentTab.equals(Constants.TabType.FILE)) {

        } else {
            if (AsymmetricAlgorithm.RSA.equals(algorithm)) {
                if (isEncrypt()) {
                    rsaService.encrypt(inputTextArea.getText(),padding.toString());
                } else {
                    rsaService.decrypt(inputTextArea.getText(),padding.toString());
                }
            } else if (AsymmetricAlgorithm.ECC.equals(algorithm)) {
                if (isEncrypt()) {
                    eccService.encrypt(inputTextArea.getText(),padding.toString());
                } else {
                    eccService.decrypt(inputTextArea.getText(),padding.toString());
                }
            }
        }

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
        return isEncrypt;
    }

    @Override
    public void setIsEncryptMode(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    @Override
    public void chooseFile() {
        IFileEncryptable.super.chooseFile();
        if (inputFile != null) {
            filePathLabel.setText(inputFile.getAbsolutePath());
        }
    }
    @Override
    public void encryptFile(File file) {

    }

    @Override
    public void decryptFile(File file) {

    }

    @Override
    public void setInputFile(File file) {
        inputFile = file;
    }

    public JLabel getFilePathLabel() {
        return filePathLabel;
    }
    public File getInputFile() {
        return inputFile;
    }
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public void setEncryptMode(boolean encryptMode) {
        isEncryptMode = encryptMode;
    }

    public void setFilePathLabel(JLabel filePathLabel) {
        this.filePathLabel = filePathLabel;
    }

    public JTextArea getInputTextArea() {
        return inputTextArea;
    }

    public void setInputTextArea(JTextArea inputTextArea) {
        this.inputTextArea = inputTextArea;
    }

    public JTextArea getOutputTextArea() {
        return outputTextArea;
    }

    public void setOutputTextArea(JTextArea outputTextArea) {
        this.outputTextArea = outputTextArea;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
    }
    public void setPublicKeyArea(JTextArea publicKeyArea) {
        this.publicKeyArea = publicKeyArea;
    }

    public void setPrivateKeyArea(JTextArea privateKeyArea) {
        this.privateKeyArea = privateKeyArea;
    }

    public Constants.TabType getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(Constants.TabType currentTab) {
        this.currentTab = currentTab;
    }

    public JLabel getMessageFileExecute() {
        return messageFileExecute;
    }

    public void setMessageFileExecute(JLabel messageFileExecute) {
        this.messageFileExecute = messageFileExecute;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public AsymmetricAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AsymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Object getPadding() {
        return padding;
    }

    public void setPadding(Object padding) {
        this.padding = padding;
    }
}
