package com.raven.form.basic;

import com.raven.controller.implement.BasicCipherController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BasicCipherForm {
    private JPanel panel;
    private JComboBox<String> algorithmComboBox;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JToggleButton modeToggleButton;
    private JButton executeButton;
    private JLabel titleLabel;
    private JLabel inputLabel;
    private JLabel outputLabel;

    private BasicCipherController controller;

    public BasicCipherForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller = new BasicCipherController(this);
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }

    private void initializeComponents() {
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        titleLabel = createStyledLabel("Basic Cipher Tool", "🔐");

        algorithmComboBox = createStyledComboBox(new String[]{
                "Polyalphabetic Cipher",
                "Caesar Shift Cipher",
                "Substitution Cipher"
        });

        inputLabel = createStyledLabel("Plain Text", "✏️");
        outputLabel = createStyledLabel("Encrypted Text", "📝");

        inputTextArea = createStyledTextArea(10, 30);
        outputTextArea = createStyledTextArea(10, 30);

        modeToggleButton = createStyledToggleButton("🔒 Encrypt Mode");
        executeButton = createStyledButton("Execute", "▶️");
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

    private JToggleButton createStyledToggleButton(String text) {
        JToggleButton button = new JToggleButton(text);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));
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
        button.setBackground(new Color(75, 75, 245));
        button.setForeground(Color.BLACK);
        return button;
    }

    private void setupLayout() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        controlPanel.setBorder(createShadowBorder());

        // Top control panel
        controlPanel.add(algorithmComboBox);
        controlPanel.add(modeToggleButton);
        controlPanel.add(executeButton);

        // Input/Output panel
        JPanel inputOutputPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Input Panel
        JPanel inputPanel = createTextPanel(inputLabel, inputTextArea);

        // Output Panel
        JPanel outputPanel = createTextPanel(outputLabel, outputTextArea);

        inputOutputPanel.add(controlPanel);
        inputOutputPanel.add(inputPanel);
        inputOutputPanel.add(outputPanel);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(inputOutputPanel, BorderLayout.CENTER);
    }

    private JPanel createTextPanel(JLabel label, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(createShadowBorder());

        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return panel;
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

    private void setupEventListeners() {
        modeToggleButton.addActionListener(e -> {
            controller.toggleMode();
            updateModeToggleButton();
        });

        executeButton.addActionListener(e -> controller.executeOperation());
    }

    private void updateModeToggleButton() {
        if (controller.isEncryptMode()) {
            modeToggleButton.setText("🔒 Encrypt Mode");
            inputLabel.setText("🏷️ Plain Text");
            outputLabel.setText("🔐 Encrypted Text");
        } else {
            modeToggleButton.setText("🔓 Decrypt Mode");
            inputLabel.setText("🔐 Encrypted Text");
            outputLabel.setText("🏷️ Plain Text");
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getSelectedAlgorithm() {
        return (String) algorithmComboBox.getSelectedItem();
    }

    public String getInputText() {
        return inputTextArea.getText();
    }

    public void setOutputText(String text) {
        outputTextArea.setText(text);
    }

    public boolean isEncryptMode() {
        return controller.isEncryptMode();
    }
}