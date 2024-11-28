package com.raven.form.asymmetric.components;

import com.raven.component.CipherModeSwitch;
import com.raven.component.ComboBox;
import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.Button;
import com.raven.constant.asymmetric.algorithm.AsymmetricAlgorithm;
import com.raven.constant.asymmetric.key.AsymmetricKeySize;
import com.raven.constant.asymmetric.padding.ECCPadding;
import com.raven.constant.asymmetric.padding.RSAPadding;
import com.raven.controller.implement.AsymmetricController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ControlPanel extends JPanel {
    private ComboBox<AsymmetricKeySize> keySizeCombo;
    private ComboBox<AsymmetricAlgorithm> cipherCombo;
    private ComboBox<Object> paddingCombo;
    private JButton generateButton;
    private JButton executeButton;

    public ControlPanel(AsymmetricController controller) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        setBorder(new CustomBorder());

        // Key Size Selection
        JLabel keySizeLabel = new Label("Key Size:", "🔑");
        keySizeCombo = new ComboBox<>(AsymmetricKeySize.values());
        keySizeCombo.addActionListener(e -> {
            AsymmetricKeySize selectedKeySize = (AsymmetricKeySize) keySizeCombo.getSelectedItem();
            controller.setKeySize(Integer.parseInt(selectedKeySize.getSize().replace(" bit", "")));  // Save key size in controller
        });

        // Cipher Type Selection
        JLabel cipherLabel = new Label("Cipher Type:", "🔐");
        cipherCombo = new ComboBox<>(AsymmetricAlgorithm.values());
        cipherCombo.addActionListener(e -> {
            AsymmetricAlgorithm selectedAlgorithm = (AsymmetricAlgorithm) cipherCombo.getSelectedItem();
            controller.setAlgorithm(selectedAlgorithm);
            updatePaddingOptions();
        });

        // Padding Selection
        JLabel paddingLabel = new Label("Padding Type:", "🔒");
        paddingCombo = new ComboBox<>(new String[0]);

        // Update padding options based on selected algorithm
        paddingCombo.addActionListener(e -> {
            if (controller.getAlgorithm() == AsymmetricAlgorithm.RSA) {
                RSAPadding selectedPadding = (RSAPadding) paddingCombo.getSelectedItem();
                controller.setPadding(selectedPadding);
            } else if (controller.getAlgorithm() == AsymmetricAlgorithm.ECC) {
                ECCPadding selectedPadding = (ECCPadding) paddingCombo.getSelectedItem();
                controller.setPadding(selectedPadding);
            }
        });

        // Generate Key Button
        generateButton = new Button("Generate Keys", "⚡");
        generateButton.setBackground(new Color(100, 180, 100));
        generateButton.setForeground(Color.BLACK);
        generateButton.addActionListener(e -> {
            try {
                controller.genarateKey();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Mode Switch
        JPanel switchPanel = new CipherModeSwitch(controller);

        // Execute Button
        executeButton = new Button("Execute", "▶️");
        executeButton.setBackground(new Color(75, 75, 245));
        executeButton.setForeground(Color.BLACK);
        executeButton.addActionListener(e -> {
            try {
                controller.execute();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Reset Button
        JButton resetButton = new Button("Reset", "🔃");
        resetButton.addActionListener(e -> controller.resetForm());

        setPreferredSize(new Dimension(600, 95));
        add(keySizeLabel);
        add(keySizeCombo);
        add(cipherLabel);
        add(cipherCombo);
        add(paddingLabel);
        add(paddingCombo);
        add(generateButton);
        add(switchPanel);
        add(executeButton);
        add(resetButton);
        updatePaddingOptions();  // Initialize padding options when panel is first created
    }

    private void updatePaddingOptions() {
        AsymmetricAlgorithm selectedCipher = (AsymmetricAlgorithm) cipherCombo.getSelectedItem();

        paddingCombo.removeAllItems();

        if (AsymmetricAlgorithm.RSA.equals(selectedCipher)) {
            // Add RSA padding options
            Arrays.stream(RSAPadding.values()).forEach(padding -> paddingCombo.addItem(padding));
        } else if (AsymmetricAlgorithm.ECC.equals(selectedCipher)) {
            // Add ECC padding options
            Arrays.stream(ECCPadding.values()).forEach(padding -> paddingCombo.addItem(padding));
        }
    }
}
