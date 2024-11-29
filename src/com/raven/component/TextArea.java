package com.raven.component;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TextArea extends JTextArea {
    public TextArea(int rows, int cols) {
        super(rows, cols);
        setFont(new Font("Arial", Font.PLAIN, 12));
        setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        setLineWrap(true);
        setWrapStyleWord(true);
        setBackground(new Color(245, 245, 245));
    }
}