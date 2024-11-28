package com.raven.form.asymmetric.components;

import com.raven.component.Button;
import com.raven.component.CustomBorder;
import com.raven.component.Label;
import com.raven.component.TextArea;
import com.raven.controller.implement.AsymmetricController;
import com.raven.form.asymmetric.AsymmetricForm;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilePanel extends JPanel {
    JLabel messageFileExecute;
    private JLabel filePathLabel;
    public FilePanel(AsymmetricController controller) {
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

        // ActionListener for Choose File button
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
        ioPanel.add(messageFileExecute);

        add(fileSelectionPanel, BorderLayout.NORTH);
        add(ioPanel, BorderLayout.CENTER);

    }
}
