package com.raven.form.asymmetric.components;

import com.raven.component.CipherModeSwitch;
import com.raven.component.ComboBox;
import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.Button;
import com.raven.constant.*;
import com.raven.controller.implement.AsymmetricController;
import com.raven.service.asymmetrical.implement.ECCService;
import com.raven.service.asymmetrical.implement.RSAService;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ControlPanel extends JPanel {
    private AsymmetricController controller;
    private JPanel topPanel;
    private ComboBox<String> algorithmCombo;
    private JButton resetButton;
    private ComboBox<Integer> keySizeCombo;
    private ComboBox<String> paddingCombo;
    private JPanel bottomPanel;
    private JButton generateKeyButton;
    private JButton executeButton;
    private String currentAlgorithm = CipherTypes.ASYMMETRIC_CIPHERS[0];
    private int currentKeyLength = KeyLengths.getKeyLengths(currentAlgorithm)[0];
    private String currentTransformation = Transformation.getTransformations(currentAlgorithm).get(0);

    public ControlPanel(AsymmetricController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBorder(new CustomBorder());

        JLabel cipherLabel = new Label("Cipher Type:", "🔐");
        algorithmCombo = new ComboBox<>(CipherTypes.ASYMMETRIC_CIPHERS);
        controller.setAlgorithmCombobo(algorithmCombo);

        JLabel keySizeLabel = new Label("Key Size:", "🔑");
        Integer[] keyLengths = Arrays.stream(KeyLengths.getKeyLengths(currentAlgorithm))
                .boxed()
                .toArray(Integer[]::new);
        keySizeCombo = new ComboBox<>(keyLengths);

        JLabel paddingLabel = new Label("Padding Type:", "🔒");
        List<String> paddingOptions = Transformation.getTransformations(currentAlgorithm);
        paddingCombo = new ComboBox<>(paddingOptions.toArray(new String[0]));

        topPanel.add(cipherLabel);
        topPanel.add(algorithmCombo);
        topPanel.add(keySizeLabel);
        topPanel.add(keySizeCombo);
        topPanel.add(paddingLabel);
        topPanel.add(paddingCombo);

        // Mode Switch Button (Cipher Mode Switch)
        JToggleButton switchPanel = new CipherModeSwitch(controller);
        topPanel.add(switchPanel);

        // Initialize and setup bottom panel for action buttons
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottomPanel.setBorder(new CustomBorder());

        // Generate Key Button
        generateKeyButton = new Button("Generate Keys", "⚡");
        generateKeyButton.setBackground(new Color(100, 180, 100));
        generateKeyButton.setForeground(Color.BLACK);


        // Execute Button
        executeButton = new Button("Execute", "▶️");
        executeButton.setBackground(new Color(75, 75, 245));
        executeButton.setForeground(Color.BLACK);

        resetButton = new Button("Reset", "🔃");
        resetButton.addActionListener(e -> controller.resetForm());

        bottomPanel.add(generateKeyButton);
        bottomPanel.add(executeButton);
        bottomPanel.add(resetButton);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        updatePaddingAndKeySizeOptions();

        addActionListener();
    }

    private void addActionListener() {

        algorithmCombo.addActionListener(e ->
        {
            currentAlgorithm = algorithmCombo.getSelectedItem().toString();
            updatePaddingAndKeySizeOptions();
            setAlgorithm();
        });
        keySizeCombo.addActionListener(e -> currentKeyLength = (int) keySizeCombo.getSelectedItem());
        paddingCombo.addActionListener(e-> currentTransformation = (String) paddingCombo.getSelectedItem());
        generateKeyButton.addActionListener(e -> controller.generateKey(currentKeyLength));
        executeButton.addActionListener(e -> {
            try {
                controller.execute(currentTransformation);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        });
    }

    private void setAlgorithm() {
        switch (currentAlgorithm) {
            case CipherAlgorithms.RSA:
                controller.setCipher(new RSAService());
                break;
                case CipherAlgorithms.ECC:
                controller.setCipher(new ECCService());
                break;
            default:
                JOptionPane.showMessageDialog(null,"Not found algorithm");
                break;

        }

    }
    private void updatePaddingAndKeySizeOptions() {
        int[] keyLengths = KeyLengths.getKeyLengths(currentAlgorithm);
        currentKeyLength = keyLengths[0];
        Integer[] keyLengthsFormatted = Arrays.stream(keyLengths)
                .boxed()
                .toArray(Integer[]::new);
        keySizeCombo.setModel(new ComboBox<>(keyLengthsFormatted).getModel());

        List<String> paddingOptions = Transformation.getTransformations(currentAlgorithm);
        currentTransformation = Transformation.getTransformations(currentAlgorithm).get(0);
        paddingCombo.setModel(new ComboBox<>(paddingOptions.toArray(new String[0])).getModel());

        revalidate();
        repaint();
    }
}
