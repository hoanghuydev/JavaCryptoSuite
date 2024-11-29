package com.raven.form.hash.components;

import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.SmallButton;
import com.raven.component.TextArea;
import com.raven.controller.implement.HashController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

public class OutputPanel extends JPanel {
    private HashController controller;
    private JTextArea outputArea;
    public OutputPanel(HashController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));
        setBorder(new CompoundBorder(
                new EmptyBorder(10, 0, 10, 0),
                new CustomBorder()
        ));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel outputLabel = new Label("Hash Output", "📋");


        JButton copyButton = new SmallButton("📋", "Copy Hash", this::copyOutputToClipboard);

        headerPanel.add(outputLabel);
        headerPanel.add(Box.createHorizontalStrut(10));
        headerPanel.add(copyButton);

        outputArea = new TextArea(8, 30);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        controller.setOutputArea(outputArea);

        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

    }
    private void copyOutputToClipboard(ActionEvent e) {
        String output = outputArea.getText().trim();
        if (!output.isEmpty()) {
            StringSelection stringSelection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            JOptionPane.showMessageDialog(null,
                    "Hash copied to clipboard",
                    "Copy Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "No hash to copy",
                    "Copy Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
