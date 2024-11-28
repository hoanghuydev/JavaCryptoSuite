package com.raven.component;

import javax.swing.*;
import java.awt.*;

public class ComboBox<T> extends JComboBox<T> {
    public ComboBox(T[] options) {
        super(options);
        setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        setPreferredSize(new Dimension(120, 30));
        setBackground(new Color(240, 240, 240));
    }
}
