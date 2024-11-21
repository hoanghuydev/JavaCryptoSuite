package com.raven.form;

import javax.swing.*;
import java.awt.*;

public class AsymmetricForm {
    private JPanel panel;

    public AsymmetricForm() {
        // Khởi tạo panel chính với GridBagLayout
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Lề giữa các thành phần
        gbc.fill = GridBagConstraints.BOTH;

        // Hàng 1: Key Size và Tạo Key
        JLabel lblKeySize = new JLabel("Key Size:");
        JComboBox<String> comboKeySize = new JComboBox<>(new String[]{"512 bit", "1024 bit", "2048 bit"});
        JButton btnGenerateKey = new JButton("Tạo Key");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(lblKeySize, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(comboKeySize, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(btnGenerateKey, gbc);

        // Hàng 2: Cipher Type
        JLabel lblCipherType = new JLabel("Cipher Type:");
        JComboBox<String> comboCipherType = new JComboBox<>(new String[]{"RSA", "DSA", "ECC"});

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblCipherType, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(comboCipherType, gbc);

        // Hàng 3: Public Key và Private Key
        JLabel lblPublicKey = new JLabel("Public Key:");
        JTextArea txtPublicKey = new JTextArea(5, 20);
        txtPublicKey.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel lblPrivateKey = new JLabel("Private Key:");
        JTextArea txtPrivateKey = new JTextArea(5, 20);
        txtPrivateKey.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(lblPublicKey, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(txtPublicKey, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(lblPrivateKey, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(txtPrivateKey, gbc);

        // Hàng 4: Input và Output
        JLabel lblInput = new JLabel("Input:");
        JTextArea txtInput = new JTextArea(8, 20);
        txtInput.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel lblOutput = new JLabel("Output:");
        JTextArea txtOutput = new JTextArea(8, 20);
        txtOutput.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(lblInput, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(txtInput, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(lblOutput, gbc);

        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(txtOutput, gbc);

        // Hàng 5: Các nút Mã hóa, Giải mã
        JButton btnEncrypt = new JButton("Mã Hóa");
        JButton btnDecrypt = new JButton("Giải Mã");
        JButton btnSwap = new JButton("↔");
        JButton btnFile = new JButton("File");

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(btnEncrypt, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(btnDecrypt, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(btnSwap, gbc);

        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(btnFile, gbc);

        // Hàng 6: Nút Reset
        JButton btnReset = new JButton("⟲");

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(btnReset, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Asymmetric Form");
            AsymmetricForm asymmetricForm = new AsymmetricForm();

            frame.setContentPane(asymmetricForm.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
