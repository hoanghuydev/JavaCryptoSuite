package com.raven.form.basic.components;

import com.raven.component.Button;
import com.raven.component.ComboBox;
import com.raven.component.CustomBorder;
import com.raven.controller.implement.BasicCipherController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ControlPanel extends JPanel{
    private JComboBox<String> algorithmComboBox;
    private JButton executeButton;
    private JToggleButton modeToggleButton;
    private BasicCipherController controller;
    public ControlPanel(BasicCipherController controller) {
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        setBorder(new CustomBorder());
        algorithmComboBox = new ComboBox<>(new String[]{
                "Polyalphabetic Cipher",
                "Caesar Shift Cipher",
                "Substitution Cipher"
        });
        modeToggleButton = createStyledToggleButton("🔒 Encrypt Mode");
        executeButton = new Button("Execute", "▶️");

        // Top control panel
        add(algorithmComboBox);
        add(modeToggleButton);
        add(executeButton);
    }
    private void updateModeToggleButton() {
        if (controller.isEncryptMode()) {
            modeToggleButton.setText("🔒 Encrypt Mode");

        } else {
            modeToggleButton.setText("🔓 Decrypt Mode");
        }
    }
    private void setupEventListeners() {
        modeToggleButton.addActionListener(e -> {
            controller.toggleMode();
            updateModeToggleButton();
        });

        executeButton.addActionListener(e -> controller.executeOperation());
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
}
