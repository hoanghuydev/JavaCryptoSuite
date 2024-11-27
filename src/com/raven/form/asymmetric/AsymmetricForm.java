package com.raven.form.asymmetric;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AsymmetricForm {
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private boolean isEncryptMode = true;
    private JTextArea publicKeyArea;
    private JTextArea privateKeyArea;
    private JTextArea inputArea;
    private JTextArea outputArea;

    public AsymmetricForm() {
        initializeComponents();
    }

    private void initializeComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Create top control panel
        JPanel controlPanel = createControlPanel();

        // Create key panel
        JPanel keyPanel = createKeyPanel();

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));


        tabbedPane.addTab("📁 File Input", createFilePanel());
        tabbedPane.addTab("💬 Text Input", createTextPanel());

        // Customize tab appearance
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setBackgroundAt(i, new Color(240, 240, 240));
        }

        // Main layout assembly
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(keyPanel, BorderLayout.CENTER);
        panel.add(tabbedPane, BorderLayout.SOUTH);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBorder(createShadowBorder());

        // Key Size Selection
        JLabel keySizeLabel = createStyledLabel("Key Size:", "🔑");
        String[] keySizes = {"512 bit", "1024 bit", "2048 bit"};
        JComboBox<String> keySizeCombo = createStyledComboBox(keySizes);

        // Cipher Type Selection
        JLabel cipherLabel = createStyledLabel("Cipher Type:", "🔐");
        String[] cipherTypes = {"RSA", "ECC"};
        JComboBox<String> cipherCombo = createStyledComboBox(cipherTypes);

        // Padiding Selection
        JLabel paddingLabel = createStyledLabel("Padding Type:", "🔒");
        String[] paddingTypes = {
                "PKCS#1 v1.5 (RSA)",
                "OAEP (RSA)",
                "ECDSA Padding (ECC)",
                "None (ECC)"
        };
        JComboBox<String> paddingCombo = createStyledComboBox(paddingTypes);

        // Generate Key Button
        JButton generateButton = createStyledButton("Generate Keys", "⚡");
        generateButton.setBackground(new Color(100, 180, 100));
        generateButton.setForeground(Color.BLACK);

        // Mode Switch
        JPanel switchPanel = createEncryptDecryptSwitch();

        // Excute Button
        JButton excuteButton = createStyledButton("Excute", "▶️");
        excuteButton.setBackground(new Color(75, 75, 245));
        excuteButton.setForeground(Color.BLACK);

        // Reset Button
        JButton resetButton = createStyledButton("Reset", "🔃");
        resetButton.addActionListener(e -> resetForm());

        panel.setPreferredSize(new Dimension(600, 95));
        panel.add(keySizeLabel);
        panel.add(keySizeCombo);
        panel.add(cipherLabel);
        panel.add(cipherCombo);
        panel.add(paddingLabel);
        panel.add(paddingCombo);
        panel.add(generateButton);
        panel.add(switchPanel);
        panel.add(excuteButton);
        panel.add(resetButton);


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
        button.addActionListener(action);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        return button;
    }

    private JPanel createKeyPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Public Key Panel
        JPanel publicKeyPanel = new JPanel(new BorderLayout(5, 5));
        publicKeyPanel.setBorder(createShadowBorder());

        // Panel cho tiêu đề và các nút
        JPanel publicKeyHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel publicKeyLabel = createStyledLabel("Public Key", "🔓");
        JButton importPublicKeyBtn = createSmallButton("⬇️", "Import Public Key", e -> importKeys(true));
        JButton exportPublicKeyBtn = createSmallButton("⬆️", "Export Public Key", e -> exportKeys(true));

        publicKeyHeaderPanel.add(publicKeyLabel);
        publicKeyHeaderPanel.add(Box.createHorizontalStrut(10));
        publicKeyHeaderPanel.add(importPublicKeyBtn);
        publicKeyHeaderPanel.add(exportPublicKeyBtn);

        publicKeyArea = createStyledTextArea(8, 30);
        publicKeyPanel.add(publicKeyHeaderPanel, BorderLayout.NORTH);
        publicKeyPanel.add(new JScrollPane(publicKeyArea), BorderLayout.CENTER);

        // Private Key Panel
        JPanel privateKeyPanel = new JPanel(new BorderLayout(5, 5));
        privateKeyPanel.setBorder(createShadowBorder());

        // Panel cho tiêu đề và các nút
        JPanel privateKeyHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel privateKeyLabel = createStyledLabel("Private Key", "🔒");
        JButton importPrivateKeyBtn = createSmallButton("⬇️", "Import Private Key", e -> importKeys(false));
        JButton exportPrivateKeyBtn = createSmallButton("⬆️", "Export Private Key", e -> exportKeys(false));

        privateKeyHeaderPanel.add(privateKeyLabel);
        privateKeyHeaderPanel.add(Box.createHorizontalStrut(10));
        privateKeyHeaderPanel.add(importPrivateKeyBtn);
        privateKeyHeaderPanel.add(exportPrivateKeyBtn);

        privateKeyArea = createStyledTextArea(8, 30);
        privateKeyPanel.add(privateKeyHeaderPanel, BorderLayout.NORTH);
        privateKeyPanel.add(new JScrollPane(privateKeyArea), BorderLayout.CENTER);

        panel.add(publicKeyPanel);
        panel.add(privateKeyPanel);

        return panel;
    }

    private JPanel createFilePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                createShadowBorder()
        ));

        // File selection area
        JPanel fileSelectionPanel = new JPanel(new BorderLayout(5, 0));
        JButton chooseFileBtn = createStyledButton("Choose File", "📂");
        JLabel filePathLabel = new JLabel("No file selected");
        filePathLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));

        fileSelectionPanel.add(chooseFileBtn, BorderLayout.WEST);
        fileSelectionPanel.add(filePathLabel, BorderLayout.CENTER);

        // Input/Output areas
        JPanel ioPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        inputArea = createStyledTextArea(8, 30);
        outputArea = createStyledTextArea(8, 30);
        outputArea.setEditable(false);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(createStyledLabel("Input", "📝"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.add(createStyledLabel("Output", "📄"), BorderLayout.NORTH);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        ioPanel.add(inputPanel);
        ioPanel.add(outputPanel);

        panel.add(fileSelectionPanel, BorderLayout.NORTH);
        panel.add(ioPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTextPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                createShadowBorder()
        ));

        // Input/Output areas in grid layout
        JPanel ioPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(createStyledLabel("Input Text", "✏️"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(createStyledTextArea(8, 30)), BorderLayout.CENTER);

        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputPanel.add(createStyledLabel("Output Text", "📝"), BorderLayout.NORTH);
        outputPanel.add(new JScrollPane(createStyledTextArea(8, 30)), BorderLayout.CENTER);

        ioPanel.add(inputPanel);
        ioPanel.add(outputPanel);

        panel.add(ioPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEncryptDecryptSwitch() {
        JPanel switchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        switchPanel.setOpaque(false);

        // Create toggle button with larger font
        JToggleButton toggleButton = new JToggleButton("Encrypt 🔒");
        toggleButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 9));
        toggleButton.setPreferredSize(new Dimension(70, 25));

        // Style the button
        toggleButton.setBackground(new Color(100, 180, 100));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(true);

        // Add hover effect
        toggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (toggleButton.isSelected()) {
                    toggleButton.setBackground(new Color(160, 90, 90));
                } else {
                    toggleButton.setBackground(new Color(90, 160, 90));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (toggleButton.isSelected()) {
                    toggleButton.setBackground(new Color(180, 100, 100));
                } else {
                    toggleButton.setBackground(new Color(100, 180, 100));
                }
            }
        });

        // Add toggle action
        toggleButton.addActionListener(e -> {
            if (!isEncryptMode) {
                toggleButton.setText("Decrypt 🔓");
                toggleButton.setBackground(new Color(180, 100, 100));
            } else {
                toggleButton.setText("Encrypt 🔒");
                toggleButton.setBackground(new Color(100, 180, 100));
            }
            isEncryptMode = !isEncryptMode;
        });

        // Create a rounded border
        toggleButton.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(x, y, width - 1, height - 1, 20, 20);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(4, 4, 4, 4);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });

        switchPanel.add(toggleButton);
        return switchPanel;
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

    private void importKeys(boolean isPublic) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(isPublic ? "Import Public Key" : "Import Private Key");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Key Files", "key"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String content = reader.lines().reduce("", (a, b) -> a + b + "\n");
                if (isPublic) {
                    publicKeyArea.setText(content);
                } else {
                    privateKeyArea.setText(content);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error importing key: " + ex.getMessage(),
                        "Import Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void exportKeys(boolean isPublic) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(isPublic ? "Export Public Key" : "Export Private Key");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Key Files", "key"));

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".key")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".key");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                String content = isPublic ? publicKeyArea.getText() : privateKeyArea.getText();
                writer.write(content);
                JOptionPane.showMessageDialog(null,
                        (isPublic ? "Public" : "Private") + " Key exported successfully!",
                        "Export Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error exporting key: " + ex.getMessage(),
                        "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void executeOperation() {
        // Placeholder for actual encryption/decryption logic
        String inputText = isEncryptMode ?
                (inputArea != null ? inputArea.getText() : "") :
                (outputArea != null ? outputArea.getText() : "");

        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please enter " + (isEncryptMode ? "text to encrypt" : "text to decrypt"),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // TODO: Implement actual asymmetric encryption/decryption logic here
            String result = isEncryptMode ?
                    "Encrypted: " + inputText :
                    "Decrypted: " + inputText;

            if (outputArea != null) {
                outputArea.setText(result);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error during " + (isEncryptMode ? "encryption" : "decryption") + ": " + ex.getMessage(),
                    "Operation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        publicKeyArea.setText("");
        privateKeyArea.setText("");
        inputArea.setText("");
        outputArea.setText("");
        isEncryptMode = true;
        // Reset other components as needed
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Enhanced Asymmetric Encryption");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            AsymmetricForm form = new AsymmetricForm();
            frame.setContentPane(form.getPanel());

            frame.pack();
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}