package com.raven.form.symmetric;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SymmetricForm {
    private JPanel panel;

    public SymmetricForm() {
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

        // Create top control panel
        JPanel controlPanel = createControlPanel();

        // Create input/output panel
        JPanel inputOutputPanel = createInputOutputPanel();

        // Main layout assembly
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(inputOutputPanel, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBorder(createShadowBorder());

        // Algorithm Selection
        JLabel algorithmLabel = createStyledLabel("Algorithm:", "🔐");
        String[] algorithms = {"AES", "DES", "Blowfish", "Serpent", "Twofish"};
        JComboBox<String> algorithmCombo = createStyledComboBox(algorithms);

        // Mode/Padding Selection
        JLabel modeLabel = createStyledLabel("Mode/Padding:", "🔒");
        String[] modes = {"ECB/PKCS5", "CBC/PKCS7"};
        JComboBox<String> modeCombo = createStyledComboBox(modes);

        // Key Size Selection
        JLabel keySizeLabel = createStyledLabel("Key Size:", "🔑");
        String[] keySizes = {"128 bit", "192 bit", "256 bit"};
        JComboBox<String> keySizeCombo = createStyledComboBox(keySizes);

        // Generate Key Button
        JButton generateKeyButton = createStyledButton("Generate Key", "⚡");
        generateKeyButton.setBackground(new Color(100, 180, 100));
        generateKeyButton.setForeground(Color.BLACK);

        // Execute Button
        JButton executeButton = createStyledButton("Execute", "▶️");
        executeButton.setBackground(new Color(75, 75, 245));
        executeButton.setForeground(Color.BLACK);

        // Reset Button
        JButton resetButton = createStyledButton("Reset", "🔃");
        resetButton.addActionListener(e -> resetForm());

        panel.setPreferredSize(new Dimension(600, 95));
        panel.add(algorithmLabel);
        panel.add(algorithmCombo);
        panel.add(modeLabel);
        panel.add(modeCombo);
        panel.add(keySizeLabel);
        panel.add(keySizeCombo);
        panel.add(generateKeyButton);
        panel.add(executeButton);
        panel.add(resetButton);

        return panel;
    }

    private JPanel createInputOutputPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Key Panel
        JPanel keyPanel = createKeyPanel();

        // Input Panel
        JPanel inputPanel = createTextPanel("Input Text", "✏️");

        // Output Panel
        JPanel outputPanel = createTextPanel("Output Text", "📝");

        panel.add(keyPanel);
        panel.add(inputPanel);
        panel.add(outputPanel);

        return panel;
    }

    private JPanel createKeyPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(createShadowBorder());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel keyLabel = createStyledLabel("Encryption Key", "🔑");
        JButton copyKeyButton = createSmallButton("📋", "Copy Key", null);
        JButton generateKeyButton = createSmallButton("⚡", "Generate Key", null);

        headerPanel.add(keyLabel);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(copyKeyButton);
        headerPanel.add(generateKeyButton);

        JTextArea keyTextArea = createStyledTextArea(3, 30);
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(keyTextArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTextPanel(String title, String icon) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(createShadowBorder());

        JLabel titleLabel = createStyledLabel(title, icon);
        JTextArea textArea = createStyledTextArea(5, 30);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return panel;
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
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        return button;
    }

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

    private Border createShadowBorder() {
        return BorderFactory.createCompoundBorder(
                new EmptyBorder(3, 3, 3, 3),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );
    }

    private void resetForm() {
        // Reset all components
        // You would replace these with your actual component references
        JOptionPane.showMessageDialog(null, "Form Reset", "Reset", JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Symmetric Encryption");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            SymmetricForm form = new SymmetricForm();
            frame.setContentPane(form.getPanel());

            frame.pack();
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}