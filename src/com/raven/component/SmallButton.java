package com.raven.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SmallButton extends JButton {
    public SmallButton(String icon, String tooltip, ActionListener action) {
        super(icon);
        setToolTipText(tooltip);
        setFont(new Font("Segoe UI Emoji", Font.PLAIN, 10));
        setMargin(new Insets(1, 1, 1, 1));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        addActionListener(action);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }
}
