package com.raven.form.asymmetric.components;

import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.SmallButton;
import com.raven.component.TextArea;
import com.raven.controller.implement.AsymmetricController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KeyPanel extends JPanel {
    private JTextArea publicKeyArea;
    private JTextArea privateKeyArea;
    public KeyPanel(AsymmetricController controller) {
        setLayout(new GridLayout(1, 2, 10, 0));
        setBorder(new EmptyBorder(10, 0, 10, 0));
        // Public Key Panel
        JPanel publicKeyPanel = new JPanel(new BorderLayout(5, 5));
        publicKeyPanel.setBorder(new CustomBorder());

        // Panel cho tiêu đề và các nút
        JPanel publicKeyHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel publicKeyLabel = new Label("Public Key", "🔓");
        JButton importPublicKeyBtn = new SmallButton("⬇️", "Import Public Key", e -> controller.importKeys(true));
        JButton exportPublicKeyBtn = new SmallButton("⬆️", "Export Public Key", e -> controller.exportKeys(true));

        publicKeyHeaderPanel.add(publicKeyLabel);
        publicKeyHeaderPanel.add(Box.createHorizontalStrut(10));
        publicKeyHeaderPanel.add(importPublicKeyBtn);
        publicKeyHeaderPanel.add(exportPublicKeyBtn);

        publicKeyArea = new TextArea(8, 30);
        controller.setPublicKeyArea(publicKeyArea);
        publicKeyPanel.add(publicKeyHeaderPanel, BorderLayout.NORTH);
        publicKeyPanel.add(new JScrollPane(publicKeyArea), BorderLayout.CENTER);

        // Private Key Panel
        JPanel privateKeyPanel = new JPanel(new BorderLayout(5, 5));
        privateKeyPanel.setBorder(new CustomBorder());

        // Panel cho tiêu đề và các nút
        JPanel privateKeyHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel privateKeyLabel = new Label("Private Key", "🔒");
        JButton importPrivateKeyBtn = new SmallButton("⬇️", "Import Private Key", e -> controller.importKeys(false));
        JButton exportPrivateKeyBtn = new SmallButton("⬆️", "Export Private Key", e -> controller.exportKeys(false));

        privateKeyHeaderPanel.add(privateKeyLabel);
        privateKeyHeaderPanel.add(Box.createHorizontalStrut(10));
        privateKeyHeaderPanel.add(importPrivateKeyBtn);
        privateKeyHeaderPanel.add(exportPrivateKeyBtn);

        privateKeyArea = new TextArea(8, 30);
        controller.setPrivateKeyArea(privateKeyArea);
        privateKeyPanel.add(privateKeyHeaderPanel, BorderLayout.NORTH);
        privateKeyPanel.add(new JScrollPane(privateKeyArea), BorderLayout.CENTER);

        add(publicKeyPanel);
        add(privateKeyPanel);
    }
}
