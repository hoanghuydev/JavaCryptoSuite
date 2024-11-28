package com.raven.controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface IKeyExportable {
     default void exportKeys(boolean isPublic) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(isPublic ? "Export Public Key" : "Export Private Key");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Key Files", "key"));

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".key")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".key");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                String content = isPublic ? getPublicKeyArea().getText() : getPrivateKeyArea().getText();
                writer.write(content);
                JOptionPane.showMessageDialog(null,
                        (isPublic ? "Public" : "Private") + " Key exported successfully!",
                        "Export Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error exporting key: " + ex.getMessage(),
                        "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    JTextArea getPrivateKeyArea();
    JTextArea getPublicKeyArea();
}
