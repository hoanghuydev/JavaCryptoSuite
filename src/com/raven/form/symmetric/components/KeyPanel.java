package com.raven.form.symmetric.components;

import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.SmallButton;
import com.raven.component.TextArea;
import com.raven.controller.implement.SymmetricController;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class KeyPanel extends JPanel {
    private SymmetricController controller;
    private JButton copyKeyButton;
    private JButton generateKeyButton;
    private JTextArea keyTextArea;

    public KeyPanel(SymmetricController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));
        setBorder(new CustomBorder());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel keyLabel = new Label("Encryption Key", "🔑");

        // Initialize buttons
        copyKeyButton = new SmallButton("📋", "Copy Key", e -> copyKeyToClipboard());
        headerPanel.add(keyLabel);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(copyKeyButton);

        // Text area to display key
        keyTextArea = new TextArea(3, 30);
        controller.setKeyTextArea(keyTextArea);
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(keyTextArea), BorderLayout.CENTER);
    }


    // Method to copy the key text to the clipboard
    private void copyKeyToClipboard() {
        String keyText = keyTextArea.getText();
        if (keyText != null && !keyText.isEmpty()) {
            StringSelection selection = new StringSelection(keyText);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(this, "Key copied to clipboard!", "Copy Key", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No key to copy!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
