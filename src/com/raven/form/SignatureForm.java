package com.raven.form;

import javax.swing.*;
import java.awt.*;

public class SignatureForm {
    private JPanel panel;

    public SignatureForm() {
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

        // Hàng 2: Khu vực chọn file
        JButton btnChooseFile = new JButton("CHỌN FILE");
        JTextArea fileArea = new JTextArea(2, 30);
        fileArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        fileArea.setEnabled(false); // Không cho chỉnh sửa

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(btnChooseFile, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(fileArea, gbc);

        // Hàng 3: Input và Nút Xác nhận
        JLabel lblInput = new JLabel("Input:");
        JTextArea txtInput = new JTextArea(8, 30);
        txtInput.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JButton btnConfirm = new JButton();
        btnConfirm.setPreferredSize(new Dimension(80, 80));
        btnConfirm.setBackground(Color.CYAN);
        btnConfirm.setIcon(new ImageIcon("path/to/checkmark-icon.png")); // Thêm biểu tượng nếu cần

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(lblInput, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(txtInput, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa nút xác nhận
        panel.add(btnConfirm, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Signature Form");
            SignatureForm signatureForm = new SignatureForm();

            frame.setContentPane(signatureForm.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}
