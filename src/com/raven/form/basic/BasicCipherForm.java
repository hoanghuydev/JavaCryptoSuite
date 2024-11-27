package com.raven.form.basic;

import com.raven.controller.BasicCipherController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
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
        controller = new BasicCipherController(this);
        initializeComponents();
        setupLayout();
        setupEventListeners();
        applyStyles();
        updateLabels();
    }

    private void initializeComponents() {
        panel = new JPanel();
        panel.setBackground(new Color(245, 245, 250));

        titleLabel = new JLabel("Basic Cipher Tool", SwingConstants.CENTER);
        inputLabel = new JLabel("Plain Text", SwingConstants.LEFT);
        outputLabel = new JLabel("Encrypted Text", SwingConstants.LEFT);

        algorithmComboBox = new JComboBox<>(new String[]{
                "Polyalphabetic Cipher",
                "Caesar Shift Cipher",
                "Substitution Cipher"
        });

        inputTextArea = createStyledTextArea("Enter text here...");
        outputTextArea = createStyledTextArea("Result will appear here...");

        modeToggleButton = new JToggleButton("🔒 Encrypt Mode");
        executeButton = createStyledButton("Execute", "Run encryption/decryption");
    }

    private JTextArea createStyledTextArea(String placeholder) {
        JTextArea textArea = new JTextArea(10, 25) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!(g instanceof Graphics2D)) {
                    super.paintComponent(g);
                    return;
                }
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền bo tròn
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Vẽ văn bản
                super.paintComponent(g2d);
                g2d.dispose();
            }
        };

        textArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setForeground(new Color(60, 60, 60));
        textArea.setText(placeholder);
        textArea.setBackground(Color.WHITE);
        textArea.setOpaque(false);
        return textArea;
    }

    private JScrollPane createStyledScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!(g instanceof Graphics2D)) {
                    super.paintComponent(g);
                    return;
                }
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Tạo hiệu ứng bóng đổ
                int shadowSize = 4;
                int shadowOffset = 2;
                for (int i = 0; i < shadowSize; i++) {
                    float opacity = 0.15f - (i * 0.03f);
                    g2d.setColor(new Color(0, 0, 0, (int)(opacity * 255)));
                    g2d.fillRoundRect(
                            shadowOffset + i,
                            shadowOffset + i,
                            getWidth() - (shadowSize + shadowOffset),
                            getHeight() - (shadowSize + shadowOffset),
                            15, 15
                    );
                }

                // Vẽ nền chính
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

                super.paintComponent(g2d);
                g2d.dispose();
            }

            @Override
            public Insets getInsets() {
                // Thêm khoảng đệm để tính đến bóng đổ
                return new Insets(5, 5, 9, 9);
            }
        };

        // Viền bo tròn tùy chỉnh
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(Color.WHITE);

        // Tùy chỉnh thanh cuộn
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(12, 0));
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(180, 180, 180);
                this.trackColor = new Color(240, 240, 240);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });

        return scrollPane;
    }

    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        return button;
    }

    private JPanel createShadowPanel(JComponent component) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                // Thêm không gian cho bóng đổ
                return new Dimension(size.width + 10, size.height + 10);
            }
        };
        panel.setOpaque(false);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void setupLayout() {
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(245, 245, 250));
        titleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        titlePanel.add(titleLabel);

        // Panel nội dung chính
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Text Area bên trái với Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        inputLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        contentPanel.add(inputLabel, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane leftScroll = createStyledScrollPane(inputTextArea);
        contentPanel.add(leftScroll, gbc);

        // Các nút điều khiển ở giữa
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints centerGbc = new GridBagConstraints();
        centerGbc.insets = new Insets(5, 5, 5, 5);
        centerGbc.fill = GridBagConstraints.HORIZONTAL;

        // Combo Box chọn thuật toán
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.weightx = 1.0;
        algorithmComboBox.setPreferredSize(new Dimension(200, 35));
        centerPanel.add(algorithmComboBox, centerGbc);

        // Nút Toggle chế độ mã hóa/giải mã
        centerGbc.gridy = 1;
        modeToggleButton.setPreferredSize(new Dimension(200, 35));
        styleToggleButton(modeToggleButton);
        centerPanel.add(modeToggleButton, centerGbc);

        // Nút thực thi
        centerGbc.gridy = 2;
        executeButton.setPreferredSize(new Dimension(200, 35));
        centerPanel.add(executeButton, centerGbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 0.2;
        contentPanel.add(centerPanel, gbc);

        // Text Area bên phải với Label
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        outputLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        contentPanel.add(outputLabel, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane rightScroll = createStyledScrollPane(outputTextArea);
        contentPanel.add(rightScroll, gbc);

        // Thêm tất cả các panel vào panel chính
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
    }

    private void styleToggleButton(JToggleButton button) {
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        button.setFocusPainted(false);
        updateToggleButtonStyle();
    }

    void updateToggleButtonStyle() {
        if (controller.isEncryptMode()) {
            modeToggleButton.setBackground(new Color(70, 130, 180));
            modeToggleButton.setForeground(Color.WHITE);
            modeToggleButton.setText("🔒 Encrypt Mode");
        } else {
            modeToggleButton.setBackground(new Color(180, 70, 70));
            modeToggleButton.setForeground(Color.WHITE);
            modeToggleButton.setText("🔓 Decrypt Mode");
        }
    }

    private void applyStyles() {
        // Tùy chỉnh Combo Box
        algorithmComboBox.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        algorithmComboBox.setBackground(Color.WHITE);

        // Tùy chỉnh Nút Execute
        Color buttonColor = new Color(70, 130, 180);
        executeButton.setBackground(buttonColor);
        executeButton.setForeground(Color.WHITE);
    }

    void updateLabels() {
        if (controller.isEncryptMode()) {
            inputLabel.setText("Plain Text");
            outputLabel.setText("Encrypted Text");
        } else {
            inputLabel.setText("Encrypted Text");
            outputLabel.setText("Plain Text");
        }
    }

    private void setupEventListeners() {
        modeToggleButton.addActionListener(e -> {
            controller.toggleMode();
            updateToggleButtonStyle();
            updateLabels();
        });

        executeButton.addActionListener(e -> controller.executeOperation());
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
