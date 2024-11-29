package com.raven.form.symmetric;

import com.raven.constant.Constants;
import com.raven.constant.Tab;
import com.raven.controller.implement.SymmetricController;
import com.raven.form.symmetric.components.ControlPanel;
import com.raven.form.symmetric.components.FilePanel;
import com.raven.form.symmetric.components.KeyPanel;
import com.raven.form.symmetric.components.TextPanel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SymmetricForm {
    private JTabbedPane tabbedPane;
    private JPanel panel;

    public SymmetricForm() {
        initializeComponents();
    }

    private void initializeComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SymmetricController controller = new SymmetricController();
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        ControlPanel controlPanel = new ControlPanel(controller);
        JPanel keyPanel = new KeyPanel(controller);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));


        tabbedPane.addTab("📁 File Input", new FilePanel(controller));
        tabbedPane.addTab("💬 Text Input", new TextPanel(controller));

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setBackgroundAt(i, new Color(240, 240, 240));
        }
        tabbedPane.addChangeListener(e -> {
            int selectedTabIndex = tabbedPane.getSelectedIndex();
            controller.setCurrentTab(Tab.fromValue(selectedTabIndex));
        });

        // Main layout assembly
        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(keyPanel, BorderLayout.CENTER);
        panel.add(tabbedPane, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Symmetric Encryption");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            SymmetricForm form = new SymmetricForm();
            frame.setContentPane(form.getPanel());
            frame.pack();
            frame.setSize(600, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}