package com.raven.form.symmetric.components;


import com.raven.component.Button;
import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.controller.implement.AsymmetricController;
import com.raven.controller.implement.SymmetricController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilePanel extends JPanel {
    private JLabel messageFileExecute;
    private JLabel filePathLabel;
    public FilePanel(SymmetricController controller) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout(10, 10));

        setBorder(new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new CustomBorder()
        ));

        JPanel fileSelectionPanel = new JPanel(new BorderLayout(5, 0));
        JButton chooseFileBtn = new Button("Choose File", "📂");
        filePathLabel = new JLabel("No file selected");
        controller.setFilePathLabel(filePathLabel);
        filePathLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));

        chooseFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.chooseFile();
            }
        });

        fileSelectionPanel.add(chooseFileBtn, BorderLayout.WEST);
        fileSelectionPanel.add(filePathLabel, BorderLayout.CENTER);

        // Input/Output areas
        JPanel ioPanel = new JPanel(new GridLayout(1, 1, 10, 0));
        messageFileExecute = new Label("Result will appear here!","👉");
        controller.setMessageFileExecute(messageFileExecute);
        ioPanel.add(messageFileExecute);

        add(fileSelectionPanel, BorderLayout.NORTH);
        add(ioPanel, BorderLayout.CENTER);

    }
}
