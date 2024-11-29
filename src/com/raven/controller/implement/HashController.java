package com.raven.controller.implement;

import com.raven.controller.ICipherSwitchable;
import com.raven.service.hash.IHashService;
import com.raven.service.hash.implement.BcryptService;
import lombok.Data;

import javax.swing.*;
@Data
public class HashController implements ICipherSwitchable<IHashService> {
    private IHashService hashService;
    private JButton resetButton;
    private JTextArea outputArea;
    private JTextArea inputArea;
    private JComboBox<String> algorithmCombo;

    public HashController() {
        hashService = new BcryptService();
    }

    public void hash() {
        String input = inputArea.getText();
        String hashedText = hashService.hash(input);
        outputArea.setText(hashedText);
    }

    public void resetForm() {
        outputArea.setText("");
        inputArea.setText("");
        algorithmCombo.setSelectedIndex(0);
    }
    @Override
    public IHashService getCipher() {
        return hashService;
    }

    @Override
    public void setCipher(IHashService cipher) {
        hashService = cipher;
    }
}
