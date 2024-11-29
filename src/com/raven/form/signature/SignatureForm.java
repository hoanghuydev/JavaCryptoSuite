package com.raven.form.signature;

import com.raven.component.*;
import com.raven.component.Button;
import com.raven.component.Label;
import com.raven.component.TextArea;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SignatureForm {
    private JPanel panel;
    private JTextArea privateKeyArea;
    private JTextArea publicKeyArea;
    private JComboBox<String> algorithmComboBox;
    private JLabel filePathArea;
    private JTextArea signatureArea;
    private JButton generateKeysButton;
    private JButton importPrivateKeyButton;
    private JButton savePrivateKeyButton;
    private JButton importPublicKeyButton;
    private JButton savePublicKeyButton;
    private JButton chooseFileButton;
    private JButton signButton;
    private JButton verifyButton;

    public SignatureForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        setupUI();
        setupActions();
    }

    private void setupUI() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Title
        JLabel titleLabel = new Label("Advanced Digital Signature", "🔐");
        panel.add(titleLabel, BorderLayout.NORTH);

        // Key Generation Section
        JPanel keyPanel = createKeyGenerationPanel(gbc);

        // Signature Generation Section
        JPanel signaturePanel = createSignaturePanel(gbc);

        // Combine Panels
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 5;
        gbc.weighty = 2;
        contentPanel.add(keyPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 3 ;
        contentPanel.add(signaturePanel, gbc);

        panel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createKeyGenerationPanel(GridBagConstraints gbc) {
        JPanel keyPanel = new JPanel(new GridBagLayout());
        keyPanel.setBorder(BorderFactory.createTitledBorder("Key Management"));

        // Generate Keys Button
        generateKeysButton = new Button("Generate Keys", "🔑");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        keyPanel.add(generateKeysButton, gbc);

        // Private Key Section
        JLabel privateKeyLabel = new Label("Private Key:", "🔒");
        privateKeyArea = new TextArea(5, 40);

        importPrivateKeyButton = new SmallButton("📂", "Import Private Key",e->importPrivateKey());
        savePrivateKeyButton = new SmallButton("💾", "Save Private Key",e->savePrivateKey());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        keyPanel.add(privateKeyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        keyPanel.add(importPrivateKeyButton, gbc);

        gbc.gridx = 2;
        keyPanel.add(savePrivateKeyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 40;
        keyPanel.add(new JScrollPane(privateKeyArea), gbc);

        // Public Key Section
        JLabel publicKeyLabel = new Label("Public Key:", "🔓");
        publicKeyArea = new TextArea(5, 40);

        importPublicKeyButton = new SmallButton("📂", "Import Public Key",e -> importPublicKey());
        savePublicKeyButton = new SmallButton("💾", "Save Public Key",e -> savePublicKey());

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        keyPanel.add(publicKeyLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        keyPanel.add(importPublicKeyButton, gbc);

        gbc.gridx = 2;
        keyPanel.add(savePublicKeyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 40;
        keyPanel.add(new JScrollPane(publicKeyArea), gbc);

        return keyPanel;
    }

    private JPanel createSignaturePanel(GridBagConstraints gbc) {
        JPanel signaturePanel = new JPanel(new GridBagLayout());
        signaturePanel.setBorder(BorderFactory.createTitledBorder("Signature Generation"));

        // Algorithm Selection
        JLabel algorithmLabel = new Label("Signature Algorithm:", "🧮");
        algorithmComboBox = new ComboBox<>(
                new String[]{"RSA", "DSA", "ECDSA", "SHA256withRSA"}
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        signaturePanel.add(algorithmLabel, gbc);

        gbc.gridx = 1;
        signaturePanel.add(algorithmComboBox, gbc);

        // File Selection
        chooseFileButton = new Button("Choose File", "📁");
        filePathArea = new Label("No file selected","");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        signaturePanel.add(chooseFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        signaturePanel.add(filePathArea, gbc);

        // Signature Actions
        signButton = new Button("Sign", "✍️");
        signButton.setBackground(new Color(100, 180, 100));
        verifyButton = new Button("Verify", "🔍");
        verifyButton.setBackground(new Color(180, 100, 100));

        signatureArea = new TextArea(5, 40);
        signatureArea.setEditable(false);

        JLabel signatureLabel = new Label("Signature:", "🔑");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        signaturePanel.add(signButton, gbc);

        gbc.gridx = 1;
        signaturePanel.add(verifyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        signaturePanel.add(signatureLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        signaturePanel.add(new JScrollPane(signatureArea), gbc);

        return signaturePanel;
    }

    private void setupActions() {
        generateKeysButton.addActionListener(e -> generateKeys());
        importPrivateKeyButton.addActionListener(e -> importPrivateKey());
        savePrivateKeyButton.addActionListener(e -> savePrivateKey());
        importPublicKeyButton.addActionListener(e -> importPublicKey());
        savePublicKeyButton.addActionListener(e -> savePublicKey());
        chooseFileButton.addActionListener(e -> chooseFile());
        signButton.addActionListener(e -> signFile());
        verifyButton.addActionListener(e -> verifySignature());
    }

    // Placeholder methods for actions
    private void generateKeys() {
        // TODO: Implement key generation logic
        privateKeyArea.setText("Generated Private Key...");
        publicKeyArea.setText("Generated Public Key...");
    }

    private void importPrivateKey() {
        // TODO: Implement private key import
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            privateKeyArea.setText("Imported Private Key from: " + selectedFile.getName());
        }
    }

    private void savePrivateKey() {
        // TODO: Implement private key save
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(panel, "Private Key saved to: " + selectedFile.getName());
        }
    }

    private void importPublicKey() {
        // TODO: Implement public key import
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            publicKeyArea.setText("Imported Public Key from: " + selectedFile.getName());
        }
    }

    private void savePublicKey() {
        // TODO: Implement public key save
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(panel, "Public Key saved to: " + selectedFile.getName());
        }
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathArea.setText(selectedFile.getAbsolutePath());
        }
    }

    private void signFile() {
        // TODO: Implement file signing logic
        String algorithm = (String) algorithmComboBox.getSelectedItem();
        signatureArea.setText("Signature generated using " + algorithm + " algorithm");
    }

    private void verifySignature() {
        // TODO: Implement signature verification logic
        JOptionPane.showMessageDialog(panel, "Signature Verification Result",
                "Verification Status", JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Advanced Digital Signature");
            SignatureForm form = new SignatureForm();

            frame.setContentPane(form.getPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}