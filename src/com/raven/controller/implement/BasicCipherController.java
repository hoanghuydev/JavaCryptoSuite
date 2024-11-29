package com.raven.controller.implement;

import com.raven.controller.ICipherSwitchable;
import com.raven.controller.IModeSwitchable;
import com.raven.controller.ITextEncryptable;
import com.raven.form.basic.BasicCipherForm;
import com.raven.service.classical.IClassicalService;
import com.raven.service.classical.implement.Base64Service;
import lombok.Data;

import javax.swing.*;
@Data
public class BasicCipherController implements ICipherSwitchable<IClassicalService>, IModeSwitchable, ITextEncryptable {
    private boolean isEncryptMode = true;
    private IClassicalService classicalService;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;

    public BasicCipherController() {
        classicalService = new Base64Service();
    }


    public boolean isEncryptMode() {
        return isEncryptMode;
    }

    @Override
    public void setIsEncryptMode(boolean isEncrypt) {
        isEncryptMode = isEncrypt;
    }


    public void executeOperation() {
        String inputText = inputTextArea.getText();
        if (isEncryptMode) {
            encrypt(inputText);
        }else {
            decrypt(inputText);
        }

    }

    @Override
    public IClassicalService getCipher() {
        return classicalService;
    }

    @Override
    public void setCipher(IClassicalService cipher) {
        classicalService = cipher;
    }

    @Override
    public void encrypt(String plainText) {
        String cipherText = classicalService.encrypt(plainText);
        outputTextArea.setText(cipherText);

    }

    @Override
    public void decrypt(String cipherText) {
        String plainText = classicalService.decrypt(cipherText);
        outputTextArea.setText(plainText);

    }
}
