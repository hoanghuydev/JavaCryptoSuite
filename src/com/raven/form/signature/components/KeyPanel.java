package com.raven.form.signature.components;
import com.raven.component.SmallButton;
import com.raven.component.Button;
import com.raven.component.TextArea;
import com.raven.component.Label;
import com.raven.controller.implement.SignController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class KeyPanel extends JPanel {
    private JTextArea privateKeyArea;
    private JTextArea publicKeyArea;
    private JButton generateKeysButton;
    private JButton importPrivateKeyButton;
    private JButton savePrivateKeyButton;
    private JButton importPublicKeyButton;
    private JButton savePublicKeyButton;
    private SignController controller;
    public KeyPanel(GridBagConstraints gbc,SignController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Key Management"));


        generateKeysButton = new Button("Generate Keys", "🔑");
        generateKeysButton.setPreferredSize(new Dimension(100, 60));
        add(generateKeysButton, gbc);


        JLabel privateKeyLabel = new Label("Private Key:", "🔒");
        privateKeyArea = new TextArea(5, 40);
        controller.setPrivateKeyArea(privateKeyArea);
        importPrivateKeyButton = new SmallButton("📂", "Import Private Key", e -> importPrivateKey());
        savePrivateKeyButton = new SmallButton("💾", "Save Private Key", e -> savePrivateKey());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(privateKeyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        add(importPrivateKeyButton, gbc);

        gbc.gridx = 2;
        add(savePrivateKeyButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(privateKeyArea), gbc);


        JLabel publicKeyLabel = new Label("Public Key:", "🔓");
        publicKeyArea = new TextArea(5, 40);
        controller.setPublicKeyArea(publicKeyArea);
        importPublicKeyButton = new SmallButton("📂", "Import Public Key", e -> importPublicKey());
        savePublicKeyButton = new SmallButton("💾", "Save Public Key", e -> savePublicKey());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(publicKeyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        add(importPublicKeyButton, gbc);

        gbc.gridx = 2;
        add(savePublicKeyButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(publicKeyArea), gbc);
        addActionListener();
    }
    private void addActionListener() {
        generateKeysButton.addActionListener(e -> controller.generateKey(2048));
    }
    private void importPrivateKey() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            privateKeyArea.setText("Imported Private Key from: " + selectedFile.getName());
        }
    }

    private void savePrivateKey() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Private Key saved to: " + selectedFile.getName());
        }
    }

    private void importPublicKey() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            publicKeyArea.setText("Imported Public Key from: " + selectedFile.getName());
        }
    }

    private void savePublicKey() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Public Key saved to: " + selectedFile.getName());
        }
    }

}
