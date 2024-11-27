package com.raven.form.hash;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

public class HashForm {
    private JPanel panel;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JComboBox<String> algorithmCombo;

    public HashForm() {
        initializeComponents();
    }

    private void initializeComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Control Panel
        JPanel controlPanel = createControlPanel();

        // Input Panel
        JPanel inputPanel = createInputPanel();

        // Output Panel
        JPanel outputPanel = createOutputPanel();

        // Main layout assembly
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(outputPanel, BorderLayout.SOUTH);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBorder(createShadowBorder());

        // Algorithm Selection
        JLabel algorithmLabel = createStyledLabel("Hash Algorithm:", "🔐");
        String[] algorithms = {"MD5", "SHA-1", "SHA-256", "SHA-512"};
        algorithmCombo = createStyledComboBox(algorithms);

        // Nút Hash
        JButton hashButton = createStyledButton("Hash", "🔍");
        hashButton.setBackground(new Color(70, 130, 180));
        hashButton.addActionListener(e -> performHash());

        // Nút Reset
        JButton resetButton = createStyledButton("Reset", "🔃");
        resetButton.addActionListener(e -> resetForm());

        // Add components
        panel.add(algorithmLabel);
        panel.add(algorithmCombo);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(hashButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(resetButton);
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new CompoundBorder(
                new EmptyBorder(10, 0, 10, 0),
                createShadowBorder()
        ));

        JLabel inputLabel = createStyledLabel("Input Text", "✏️");
        inputArea = createStyledTextArea(8, 30);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);

        panel.add(inputLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new CompoundBorder(
                new EmptyBorder(10, 0, 10, 0),
                createShadowBorder()
        ));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel outputLabel = createStyledLabel("Hash Output", "📋");

        // Nút Copy
        JButton copyButton = createSmallButton("📋", "Copy Hash", this::copyOutputToClipboard);

        headerPanel.add(outputLabel);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(copyButton);

        outputArea = createStyledTextArea(8, 30);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        return panel;
    }

    private void performHash() {
        String input = inputArea.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please enter text to hash",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String selectedAlgorithm = (String) algorithmCombo.getSelectedItem();
            String hashedResult = hashText(input, selectedAlgorithm);
            outputArea.setText(hashedResult);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error hashing text: " + ex.getMessage(),
                    "Hash Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String hashText(String input, String algorithm) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance(algorithm);
            byte[] hash = digest.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            // Convert to hex
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hashing error", e);
        }
    }

    private void copyOutputToClipboard(ActionEvent e) {
        String output = outputArea.getText().trim();
        if (!output.isEmpty()) {
            StringSelection stringSelection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            JOptionPane.showMessageDialog(null,
                    "Hash copied to clipboard",
                    "Copy Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "No hash to copy",
                    "Copy Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void resetForm() {
        inputArea.setText("");
        outputArea.setText("");
        algorithmCombo.setSelectedIndex(0);
    }

    // Các phương thức tiện ích như createStyledLabel, createStyledButton, etc.
    // sẽ giống như trong AsymmetricForm
    private JLabel createStyledLabel(String text, String icon) {
        JLabel label = new JLabel(icon + " " + text);
        label.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        return label;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        comboBox.setPreferredSize(new Dimension(120, 25));
        return comboBox;
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

    private JButton createSmallButton(String icon, String tooltip, ActionListener action) {
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));
        button.setPreferredSize(new Dimension(30, 25));
        button.setMargin(new Insets(1, 1, 1, 1));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.addActionListener(action);
        return button;
    }

    private JTextArea createStyledTextArea(int rows, int cols) {
        JTextArea textArea = new JTextArea(rows, cols);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        return textArea;
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
            JFrame frame = new JFrame("Enhanced Hash Generator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            HashForm hashForm = new HashForm();
            frame.setContentPane(hashForm.getPanel());

            frame.pack();
            frame.setSize(700, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}