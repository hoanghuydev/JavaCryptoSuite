package com.raven.component;

import com.raven.controller.IModeSwitchable;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CipherModeSwitch extends JPanel {
    private IModeSwitchable iModeSwitchable;
    private boolean isEncryptMode = true; // Khai báo biến để theo dõi chế độ

    public CipherModeSwitch(IModeSwitchable iModeSwitchable) {
        this.iModeSwitchable = iModeSwitchable;
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        setOpaque(false);

        // Tạo nút toggle với font chữ lớn hơn
        JToggleButton toggleButton = new JToggleButton("Encrypt 🔒");
        toggleButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 9));
        toggleButton.setPreferredSize(new Dimension(100, 35)); // Cập nhật kích thước nút

        // Style cho nút toggle
        toggleButton.setBackground(new Color(100, 180, 100));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setBorderPainted(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(true);

        // Hiệu ứng hover cho nút toggle
        toggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (toggleButton.isSelected()) {
                    toggleButton.setBackground(new Color(160, 90, 90)); // Đổi màu khi ở chế độ Decrypt
                } else {
                    toggleButton.setBackground(new Color(90, 160, 90)); // Đổi màu khi ở chế độ Encrypt
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (toggleButton.isSelected()) {
                    toggleButton.setBackground(new Color(180, 100, 100));
                } else {
                    toggleButton.setBackground(new Color(100, 180, 100));
                }
            }
        });

        // Thêm hành động khi nhấn nút toggle
        toggleButton.addActionListener(e -> {
            if (!iModeSwitchable.isEncryptMode()) {
                toggleButton.setText("Decrypt 🔓");
                toggleButton.setBackground(new Color(180, 100, 100));
            } else {
                toggleButton.setText("Encrypt 🔒");
                toggleButton.setBackground(new Color(100, 180, 100));
            }
            iModeSwitchable.toggleMode();
        });

        // Tạo border bo góc cho nút toggle
        toggleButton.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(x, y, width - 1, height - 1, 20, 20); // Bo góc 20px
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(4, 4, 4, 4); // Cách đều từ 4px
            }

            @Override
            public boolean isBorderOpaque() {
                return true; // Border là không trong suốt
            }
        });

        add(toggleButton); // Thêm toggle button vào panel
    }
}
