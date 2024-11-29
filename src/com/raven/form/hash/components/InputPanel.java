package com.raven.form.hash.components;

import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.TextArea;
import com.raven.controller.implement.HashController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputPanel extends JPanel {
    private HashController controller;
    private JTextArea inputArea;
    public InputPanel(HashController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));
        setBorder(new CompoundBorder(
                new EmptyBorder(10, 0, 10, 0),
                new CustomBorder()
        ));

        JLabel inputLabel = new Label("Input Text", "✏️");
        inputArea = new TextArea(8, 30);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        controller.setInputArea(inputArea);
        add(inputLabel, BorderLayout.NORTH);
        add(new JScrollPane(inputArea), BorderLayout.CENTER);

    }
}
