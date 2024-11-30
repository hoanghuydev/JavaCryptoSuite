package com.raven.controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public interface IKeyImportable {
    default void importKeys(boolean isPublic) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(isPublic ? "Import Public Key" : "Import Private Key");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Key Files", "key"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");  // Only add a newline if there's more content
                }
                // Remove the last newline if it exists
                if (content.length() > 0 && content.charAt(content.length() - 1) == '\n') {
                    content.deleteCharAt(content.length() - 1);
                }

                if (isPublic) {
                    getPublicKeyArea().setText(content.toString());
                } else {
                    getPrivateKeyArea().setText(content.toString());
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error importing key: " + ex.getMessage(),
                        "Import Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    JTextArea getPrivateKeyArea();
    JTextArea getPublicKeyArea();
}
