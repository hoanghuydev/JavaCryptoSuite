package com.raven.form.basic;

import com.raven.component.*;
import com.raven.component.Button;
import com.raven.component.Label;
import com.raven.component.TextArea;
import com.raven.controller.implement.BasicCipherController;
import com.raven.form.basic.components.ControlPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BasicCipherForm {
    private JPanel panel;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JLabel titleLabel;
    private JLabel inputLabel;
    private JLabel outputLabel;

    private BasicCipherController controller;

    public BasicCipherForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller = new BasicCipherController();
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titleLabel = new com.raven.component.Label("Basic Cipher Tool", "🔐");



        inputLabel = new com.raven.component.Label("Plain Text", "✏️");
        outputLabel = new Label("Encrypted Text", "📝");

        inputTextArea = new com.raven.component.TextArea(10, 30);
        outputTextArea = new TextArea(10, 30);


    }






    private void setupLayout() {

        // Input/Output panel
        JPanel inputOutputPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Input Panel
        JPanel inputPanel = createTextPanel(inputLabel, inputTextArea);

        // Output Panel
        JPanel outputPanel = createTextPanel(outputLabel, outputTextArea);

        inputOutputPanel.add(new ControlPanel(controller));
        inputOutputPanel.add(inputPanel);
        inputOutputPanel.add(outputPanel);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(inputOutputPanel, BorderLayout.CENTER);
    }

    private JPanel createTextPanel(JLabel label, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new CustomBorder());

        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        return panel;
    }




    public JPanel getPanel() {
        return panel;
    }

}