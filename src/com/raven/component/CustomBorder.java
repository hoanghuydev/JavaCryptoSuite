package com.raven.component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomBorder implements Border {
    private javax.swing.border.Border border;

    public CustomBorder() {
        // Tạo một CompoundBorder và gán nó cho biến 'border'
        border = BorderFactory.createCompoundBorder(
                new EmptyBorder(3, 3, 3, 3),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );
    }

    // Implement phương thức 'paintBorder' để vẽ border
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        border.paintBorder(c, g, x, y, width, height);
    }

    // Implement phương thức 'getBorderInsets' để trả về kích thước insets của border
    @Override
    public Insets getBorderInsets(Component c) {
        return border.getBorderInsets(c);
    }

    // Implement phương thức 'isBorderOpaque' để xác định border có trong suốt hay không
    @Override
    public boolean isBorderOpaque() {
        return border.isBorderOpaque();
    }
}

