package com.raven.form.symmetric.components;

import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.SmallButton;
import com.raven.component.TextArea;
import com.raven.controller.implement.AsymmetricController;
import com.raven.controller.implement.SymmetricController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class TextPanel extends JPanel {
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    public TextPanel(SymmetricController controller) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new CustomBorder()
        ));

        JPanel ioPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputTextArea =new com.raven.component.TextArea(8, 30);
        controller.setInputTextArea(inputTextArea);
        inputPanel.add(new com.raven.component.Label("Input Text", "✏️"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);

        JPanel outputPanel = new JPanel(new BorderLayout(5, 5));
        outputTextArea = new TextArea(8, 30);
        outputTextArea.setEditable(true);
        controller.setOutputTextArea(outputTextArea);
        outputPanel.add(new Label("Output Text", "📝"), BorderLayout.NORTH);
        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        ioPanel.add(inputPanel);
        ioPanel.add(outputPanel);

        add(ioPanel, BorderLayout.CENTER);
    }
}
