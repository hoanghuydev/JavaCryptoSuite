package com.raven.form.symmetric;

import javax.swing.*;
import java.awt.*;

public class SymmetricForm {
    private JPanel panel;

    public SymmetricForm() {
        // Khởi tạo panel chính với GridBagLayout
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Lề giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: Chọn giải thuật và Mode/Padding
        JLabel lblAlgorithm = new JLabel("Chọn giải thuật:");
        JComboBox<String> comboAlgorithm = new JComboBox<>(new String[]{"AES", "DES", "RSA"});
        JLabel lblMode = new JLabel("Mode/Padding:");
        JComboBox<String> comboMode = new JComboBox<>(new String[]{"ECB/PKCS5", "CBC/PKCS7"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(lblAlgorithm, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(comboAlgorithm, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(lblMode, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(comboMode, gbc);

        // Hàng 2: Key và các nút liên quan
        JLabel lblKey = new JLabel("Key:");
        JTextField txtKey = new JTextField(15);
        JComboBox<String> comboKeySize = new JComboBox<>(new String[]{"128 bit", "192 bit", "256 bit"});
        JButton btnGenerateKey = new JButton("Tạo Key");
        JButton btnCopyKey = new JButton("Copy");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblKey, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(txtKey, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(comboKeySize, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        panel.add(btnGenerateKey, gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        panel.add(btnCopyKey, gbc);

        // Hàng 3: Nhập đoạn văn bản cần mã hóa
        JTextArea txtInput = new JTextArea(3, 30);
        txtInput.setBorder(BorderFactory.createTitledBorder("Nhập đoạn văn bản cần mã hóa tại đây..."));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        panel.add(txtInput, gbc);

        // Hàng 4: Đoạn văn bản mã hóa và nút Copy
        JTextArea txtEncrypted = new JTextArea(3, 30);
        txtEncrypted.setBorder(BorderFactory.createTitledBorder("Đoạn văn bản mã hóa"));
        JButton btnCopyEncrypted = new JButton("Copy");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        panel.add(txtEncrypted, gbc);

        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(btnCopyEncrypted, gbc);

        // Hàng 5: Đoạn văn bản giải mã
        JTextArea txtDecrypted = new JTextArea(3, 30);
        txtDecrypted.setBorder(BorderFactory.createTitledBorder("Đoạn văn bản giải mã"));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 6;
        panel.add(txtDecrypted, gbc);

        // Hàng 6: Các nút Mã hóa, Giải mã, Làm mới, File
        JButton btnEncrypt = new JButton("Mã hóa");
        JButton btnDecrypt = new JButton("Giải mã");
        JButton btnReset = new JButton("Làm mới");
        JButton btnFile = new JButton("File");

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(btnEncrypt, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(btnDecrypt, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        panel.add(btnReset, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(btnFile, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Symmetric Form");
            SymmetricForm symmetricForm = new SymmetricForm();

            frame.setContentPane(symmetricForm.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
