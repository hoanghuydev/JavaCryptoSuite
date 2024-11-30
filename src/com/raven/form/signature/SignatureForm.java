package com.raven.form.signature;

import com.raven.component.*;
import com.raven.component.Button;
import com.raven.component.Label;
import com.raven.component.TextArea;
import com.raven.constant.CipherTypes;
import com.raven.controller.implement.SignController;
import com.raven.form.signature.components.KeyPanel;
import com.raven.form.signature.components.SignPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SignatureForm {
    private JPanel panel;
    private SignController controller;

    public SignatureForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        controller = new SignController();

        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        setupUI();
    }

    private void setupUI() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        JLabel titleLabel = new Label("Advanced Digital Signature", "🔐");
        panel.add(titleLabel, BorderLayout.NORTH);
        JPanel keyPanel = new KeyPanel(gbc,controller);
        JPanel signaturePanel = new SignPanel(gbc,controller);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 5;
        gbc.weighty = 2;
        contentPanel.add(keyPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 3 ;
        contentPanel.add(signaturePanel, gbc);

        panel.add(contentPanel, BorderLayout.CENTER);
    }





    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Advanced Digital Signature");
            SignatureForm form = new SignatureForm();

            frame.setContentPane(form.getPanel());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(800, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}