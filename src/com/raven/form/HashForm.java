package com.raven.form;

import javax.swing.*;
import java.awt.*;

public class HashForm {
    private JPanel panel;

    public HashForm() {
        // Khởi tạo panel chính với GridBagLayout
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Lề giữa các thành phần
        gbc.fill = GridBagConstraints.BOTH;

        // Hàng 1: Chọn giải thuật và Nút Reset
        JLabel lblAlgorithm = new JLabel("Chọn giải thuật:");
        JComboBox<String> comboAlgorithm = new JComboBox<>(new String[]{"MD5", "SHA-1", "SHA-256"});
        JButton btnReset = new JButton("⟲");

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
        panel.add(btnReset, gbc);

        // Hàng 2: Input
        JLabel lblInput = new JLabel("Input:");
        JTextArea txtInput = new JTextArea(8, 30);
        txtInput.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(lblInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        panel.add(txtInput, gbc);

        // Hàng 3: Nút Hash
        JButton btnHash = new JButton("HASH");

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(btnHash, gbc);

        // Hàng 4: Output và Nút Copy
        JLabel lblOutput = new JLabel("Output:");
        JTextArea txtOutput = new JTextArea(8, 30);
        txtOutput.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JButton btnCopy = new JButton("COPY");

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(lblOutput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(txtOutput, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(btnCopy, gbc);

        // Hàng 5: Nút File
        JButton btnFile = new JButton("FILE");

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(btnFile, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hash Form");
            HashForm hashForm = new HashForm();

            frame.setContentPane(hashForm.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}
