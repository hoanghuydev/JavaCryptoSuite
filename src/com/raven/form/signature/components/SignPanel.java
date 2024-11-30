package com.raven.form.signature.components;

import com.raven.component.ComboBox;
import com.raven.component.Label;
import com.raven.constant.CipherTypes;
import com.raven.controller.implement.SignController;

import javax.swing.*;
import java.awt.*;

public class SignPanel extends JPanel {
    private JComboBox<String> algorithmComboBox;
    private JLabel filePathArea;
    private JTextArea signatureArea;
    private JButton chooseFileButton;
    private JButton signButton;
    private JButton verifyButton;
    private SignController controller;
    public SignPanel(GridBagConstraints gbc,SignController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Signature Generation"));

        // Algorithm Selection
        JLabel algorithmLabel = new com.raven.component.Label("Signature Algorithm:", "🧮");
        algorithmComboBox = new ComboBox<>(
                CipherTypes.SIGN_CIPHERS
        );

//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        add(algorithmLabel, gbc);
//
//        gbc.gridx = 1;
//        add(algorithmComboBox, gbc);

        // File Selection
        chooseFileButton = new com.raven.component.Button("Choose File", "📁");
        filePathArea = new com.raven.component.Label("No file selected","");
        controller.setFilePathArea(filePathArea);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        add(chooseFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(filePathArea, gbc);

        // Signature Actions
        signButton = new com.raven.component.Button("Sign", "✍️");
        signButton.setBackground(new Color(100, 180, 100));
        verifyButton = new com.raven.component.Button("Verify", "🔍");
        verifyButton.setBackground(new Color(180, 100, 100));

        signatureArea = new com.raven.component.TextArea(5, 40);
        controller.setSignatureArea(signatureArea);

        JLabel signatureLabel = new Label("Signature:", "🔑");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(signButton, gbc);

        gbc.gridx = 1;
        add(verifyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(signatureLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(new JScrollPane(signatureArea), gbc);
        addActionListener();
    }
    private void addActionListener() {
        signButton.addActionListener(e-> controller.sign());
        verifyButton.addActionListener(e-> controller.verify());
        chooseFileButton.addActionListener(e-> controller.chooseFile());
    }
}
