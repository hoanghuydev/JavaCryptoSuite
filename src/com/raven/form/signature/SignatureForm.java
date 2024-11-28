package com.raven.form.signature;

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
        mainPanel.setBorder(createShadowBorder());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = createStyledLabel("Digital Signature Generator", "✍️");
        panel.add(titleLabel, BorderLayout.NORTH);

        // Hàng 1: Chọn thuật toán và nút reset
        JLabel lblAlgorithm = createStyledLabel("Algorithm:", "🔐");
        JComboBox<String> comboAlgorithm = createStyledComboBox(
                new String[]{"MD5", "SHA-1", "SHA-256", "SHA-512"}
        );
        JButton btnReset = createSmallButton("🔃", "Reset Form", e -> resetForm());

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
        JButton btnChooseFile = createStyledButton("Choose File", "📁");
        JTextArea fileArea = createStyledTextArea(2, 30);
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
        JLabel lblInput = createStyledLabel("Input Text:", "✏️");
        JTextArea inputArea = createStyledTextArea(8, 30);
        JButton btnConfirm = createStyledButton("Confirm", "✅");
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
        JLabel lblOutput = createStyledLabel("Signature:", "🔑");
        JTextArea outputArea = createStyledTextArea(4, 30);
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

    private JLabel createStyledLabel(String text, String icon) {
        JLabel label = new JLabel(icon + " " + text);
        label.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        return label;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        comboBox.setPreferredSize(new Dimension(200, 25));
        return comboBox;
    }

    private JTextArea createStyledTextArea(int rows, int cols) {
        JTextArea textArea = new JTextArea(rows, cols);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        return textArea;
    }

    private JButton createSmallButton(String icon, String tooltip, ActionListener action) {
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));
        button.setPreferredSize(new Dimension(30, 25));
        button.setMargin(new Insets(1, 1, 1, 1));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        if (action != null) {
            button.addActionListener(action);
        }
        return button;
    }

    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return button;
    }

    private Border createShadowBorder() {
        return BorderFactory.createCompoundBorder(
                new EmptyBorder(3, 3, 3, 3),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );
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