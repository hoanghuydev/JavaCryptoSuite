package com.raven.form.symmetric.components;

import com.raven.component.*;
import com.raven.component.Button;
import com.raven.component.Label;
import com.raven.constant.*;
import com.raven.controller.implement.SymmetricController;
import com.raven.service.symmetrical.implement.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ControlPanel extends JPanel {
    private SymmetricController controller;
    private JPanel topPanel;
    private ComboBox<String> algorithmCombo;
    private ComboBox<Integer> keySizeCombo;
    private ComboBox<String> paddingCombo;
    private JButton generateKeyButton;
    private JPanel bottomPanel;
    private JButton executeButton;
    private JButton resetButton;
    private String currentAlgorithm = CipherTypes.SYMMETRIC_CIPHERS[0];
    private int currentKeyLength = KeyLengths.getKeyLengths(currentAlgorithm)[0];
    private String currentTransformation = Transformation.getTransformations(currentAlgorithm).get(0);

    public ControlPanel(SymmetricController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBorder(new CustomBorder());

        JLabel algorithmLabel = new com.raven.component.Label("Algorithm:", "🔐");
        algorithmCombo = new ComboBox<>(CipherTypes.SYMMETRIC_CIPHERS);

        JLabel modeLabel = new com.raven.component.Label("Mode/Padding:", "🔒");
        paddingCombo = new ComboBox<>(new String[0]);

        JLabel keySizeLabel = new Label("Key Size:", "🔑");

        keySizeCombo = new ComboBox<>(new Integer[0]);

        generateKeyButton = new Button("Generate Key", "⚡");
        generateKeyButton.setBackground(new Color(100, 180, 100));
        generateKeyButton.setForeground(Color.BLACK);

        topPanel.add(algorithmLabel);
        topPanel.add(algorithmCombo);
        topPanel.add(modeLabel);
        topPanel.add(paddingCombo);
        topPanel.add(keySizeLabel);
        topPanel.add(keySizeCombo);

        JToggleButton switchButton = new CipherModeSwitch(controller);
        topPanel.add(switchButton);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bottomPanel.setBorder(new CustomBorder());

        executeButton = new Button("Execute", "▶️");
        executeButton.setBackground(new Color(100, 180, 100));
        executeButton.setForeground(Color.BLACK);

        resetButton = new Button("Reset", "🔃");
        resetButton.addActionListener(e -> resetForm());

        bottomPanel.add(generateKeyButton);
        bottomPanel.add(executeButton);
        bottomPanel.add(resetButton);
        updatePaddingAndKeySizeOptions();
        addActionListener();

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void resetForm() {
        controller.resetForm();
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
        executeButton.addActionListener(e -> executeHandler());
        resetButton.addActionListener(e-> resetForm());
    }
    private void setAlgorithm() {
        switch (currentAlgorithm) {
            case CipherAlgorithms.AES:
                controller.setCipher(new AESService());
                break;
            case CipherAlgorithms.DES:
                controller.setCipher(new DESService());
                break;
            case CipherAlgorithms.SERPENT:
                controller.setCipher(new SerpentService());
                break;
            case CipherAlgorithms.TWOFISH:
                controller.setCipher(new TwofishService());
                break;
            case CipherAlgorithms.BLOWFISH:
                controller.setCipher(new BlowfishService());
                break;
            default:
                JOptionPane.showMessageDialog(null,"Not found algorithm");
                break;
        }
    }
    private void executeHandler() {
        try {
            controller.execute(currentTransformation);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
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

