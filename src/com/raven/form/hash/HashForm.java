package com.raven.form.hash;

import com.raven.controller.implement.HashController;
import com.raven.form.hash.components.ControlPanel;
import com.raven.form.hash.components.InputPanel;
import com.raven.form.hash.components.OutputPanel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

public class HashForm {
    private JPanel panel;
    private HashController controller;

    public HashForm() {
        initializeComponents();
    }

    private void initializeComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        controller = new HashController();
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel controlPanel = new ControlPanel(controller);


        JPanel inputPanel = new InputPanel(controller);


        JPanel outputPanel = new OutputPanel(controller);


        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(outputPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Enhanced Hash Generator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            HashForm hashForm = new HashForm();
            frame.setContentPane(hashForm.getPanel());

            frame.pack();
            frame.setSize(700, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}