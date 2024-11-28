package com.raven.component;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    public Label(String text, String icon){
        super(icon+" "+text, JLabel.LEFT);
        setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
    }
}
