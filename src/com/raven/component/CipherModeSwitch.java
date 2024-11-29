package com.raven.component;

import com.raven.controller.IModeSwitchable;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CipherModeSwitch extends JToggleButton {
    private IModeSwitchable iModeSwitchable;

    public CipherModeSwitch(IModeSwitchable iModeSwitchable) {
        // Initialize the state
        super("Encrypt 🔒");  // Default text for Encrypt mode
        this.iModeSwitchable = iModeSwitchable;

        // Button settings (size, font, background color, etc.)
        setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));  // Larger font size
        setBackground(new Color(100, 180, 100));
        setForeground(Color.WHITE);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);

        // Add action listener to toggle between encrypt/decrypt mode
        addActionListener(e -> {
            if (iModeSwitchable.isEncryptMode()) {
                setText("Decrypt 🔓");
                setBackground(new Color(180, 100, 100));  // Decrypt mode background color
            } else {
                setText("Encrypt 🔒");
                setBackground(new Color(100, 180, 100));  // Encrypt mode background color
            }
            iModeSwitchable.toggleMode();  // Switch between modes
        });

        setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 10, 5, 10)
        ));
    }
}
