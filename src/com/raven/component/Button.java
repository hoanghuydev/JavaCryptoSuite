package com.raven.component;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Button extends JButton {
    public Button(String text, String icon) {

        super(icon + " " + text);
        setFocusPainted(false);
        setBorder(new CompoundBorder(
                        new LineBorder(new Color(200, 200, 200)),
                        new EmptyBorder(5, 10, 5, 10)));
        setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        setBackground(new Color(70, 130, 180));
        setForeground(Color.BLACK);
    }
}