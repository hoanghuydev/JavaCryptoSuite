package com.raven.controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public interface IFileEncryptable {
    default void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file to execute");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            setInputFile(fileChooser.getSelectedFile());
        }
    }
    void encryptFile(File file);
    void decryptFile(File file);
    void setInputFile(File file);
}
