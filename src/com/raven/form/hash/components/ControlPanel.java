package com.raven.form.hash.components;

import com.raven.component.Button;
import com.raven.component.ComboBox;
import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.constant.CipherAlgorithms;
import com.raven.constant.CipherTypes;
import com.raven.controller.implement.HashController;
import com.raven.service.asymmetrical.implement.ECCService;
import com.raven.service.asymmetrical.implement.RSAService;
import com.raven.service.hash.implement.BcryptService;
import com.raven.service.hash.implement.MD5Service;
import com.raven.service.hash.implement.SHAService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private HashController controller;
    private JComboBox<String> algorithmCombo;
    private JButton hashButton;
    private JButton resetButton;
    private String currentAlgorithm = CipherTypes.HASH_CIPHERS[0];
    public ControlPanel(HashController controller) {
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        setBorder(new CustomBorder());


        JLabel algorithmLabel = new Label("Hash Algorithm:", "🔐");
        algorithmCombo = new ComboBox<>(CipherTypes.HASH_CIPHERS);
        controller.setAlgorithmCombo(algorithmCombo);


        hashButton = new com.raven.component.Button("Hash", "🔍");
        hashButton.setBackground(new Color(70, 130, 180));


        resetButton = new Button("Reset", "🔃");
        resetButton.addActionListener(e -> resetForm());


        add(algorithmLabel);
        add(algorithmCombo);
        add(Box.createHorizontalStrut(10));
        add(hashButton);
        add(Box.createHorizontalStrut(10));
        add(resetButton);
        addActionListener();
    }
    private void addActionListener() {
        algorithmCombo.addActionListener(e ->
        {
            currentAlgorithm = algorithmCombo.getSelectedItem().toString();
            setAlgorithm();
        });
        hashButton.addActionListener(e-> controller.hash());
        resetButton.addActionListener(e-> controller.resetForm());
    }
    private void setAlgorithm() {
        switch (currentAlgorithm) {
            case CipherAlgorithms.BCRYPT:
                controller.setCipher(new BcryptService());
                break;
            case CipherAlgorithms.MD5:
                controller.setCipher(new MD5Service());
                break;
            case CipherAlgorithms.SHA256:
                controller.setCipher(new SHAService());
                break;
            default:
                JOptionPane.showMessageDialog(null,"Not found algorithm");
                break;

        }

    }
    private void resetForm() {

    }

}
