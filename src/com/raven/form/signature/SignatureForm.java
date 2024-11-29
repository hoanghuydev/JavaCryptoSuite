package com.raven.form.signature;

import com.raven.component.*;
import com.raven.component.Button;
import com.raven.component.Label;
import com.raven.component.TextArea;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignatureForm {
    private JPanel panel;

    public SignatureForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setupComponents();
    }

    private void setupComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new CustomBorder());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new Label("Digital Signature Generator", "✍️");
        panel.add(titleLabel, BorderLayout.NORTH);

        // Hàng 1: Chọn thuật toán và nút reset
        JLabel lblAlgorithm = new Label("Algorithm:", "🔐");
        JComboBox<String> comboAlgorithm = new ComboBox<>(
                new String[]{"MD5", "SHA-1", "SHA-256", "SHA-512"}
        );
        JButton btnReset = new SmallButton("🔃", "Reset Form", e -> resetForm());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        mainPanel.add(lblAlgorithm, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(comboAlgorithm, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.2;
        mainPanel.add(btnReset, gbc);

        // Hàng 2: Chọn file
        JButton btnChooseFile = new Button("Choose File", "📁");
        JTextArea fileArea = new TextArea(2, 30);
        fileArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        mainPanel.add(btnChooseFile, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        mainPanel.add(new JScrollPane(fileArea), gbc);

        // Hàng 3: Input và nút xác nhận
        JLabel lblInput = new Label("Input Text:", "✏️");
        JTextArea inputArea = new TextArea(8, 30);
        JButton btnConfirm = new Button("Confirm", "✅");
        btnConfirm.setBackground(new Color(100, 180, 100)); // Màu xanh lá nhạt

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        mainPanel.add(lblInput, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.6;
        mainPanel.add(new JScrollPane(inputArea), gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        mainPanel.add(btnConfirm, gbc);

        // Output area
        JLabel lblOutput = new Label("Signature:", "🔑");
        JTextArea outputArea = new TextArea(4, 30);
        outputArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        mainPanel.add(lblOutput, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        mainPanel.add(new JScrollPane(outputArea), gbc);

        panel.add(mainPanel, BorderLayout.CENTER);
    }

    private void resetForm() {
        JOptionPane.showMessageDialog(null, "Form Reset", "Reset", JOptionPane.INFORMATION_MESSAGE);
    }
    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Digital Signature");
            SignatureForm signatureForm = new SignatureForm();

            frame.setContentPane(signatureForm.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(700, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}