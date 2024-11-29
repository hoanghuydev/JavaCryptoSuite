package com.raven.form.basic.components;

import com.raven.component.Button;
import com.raven.component.CipherModeSwitch;
import com.raven.component.ComboBox;
import com.raven.component.CustomBorder;
import com.raven.constant.CipherAlgorithms;
import com.raven.constant.CipherTypes;
import com.raven.controller.implement.BasicCipherController;
import com.raven.service.asymmetrical.implement.ECCService;
import com.raven.service.asymmetrical.implement.RSAService;
import com.raven.service.classical.implement.Base64Service;
import com.raven.service.classical.implement.CaesarService;
import com.raven.service.classical.implement.PolyalphabeticService;
import com.raven.service.classical.implement.SubstitutionService;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ControlPanel extends JPanel{
    private JComboBox<String> algorithmCombo;
    private JButton executeButton;
    private JToggleButton modeToggleButton;
    private BasicCipherController controller;
    private String currentAlgorithm = CipherTypes.BASIC_CIPHERS[0];

    public ControlPanel(BasicCipherController controller) {
        this.controller = controller;
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        setBorder(new CustomBorder());
        algorithmCombo = new ComboBox<>(CipherTypes.BASIC_CIPHERS);
        modeToggleButton = new CipherModeSwitch(controller);
        executeButton = new Button("Execute", "▶️");

        add(algorithmCombo);
        add(modeToggleButton);
        add(executeButton);
        addActionListener();
    }

    private void addActionListener() {
        algorithmCombo.addActionListener(e ->
        {
            currentAlgorithm = algorithmCombo.getSelectedItem().toString();
            setAlgorithm();
        });
        executeButton.addActionListener(e -> {
            try{
                controller.executeOperation();
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }
    private void setAlgorithm() {
        switch (currentAlgorithm) {
            case CipherAlgorithms.BASE64:
                controller.setCipher(new Base64Service());
                break;
            case CipherAlgorithms.SUBSTITUTION:
                controller.setCipher(new SubstitutionService());
                break;
            case CipherAlgorithms.POLYALPHA:
                controller.setCipher(new PolyalphabeticService());
                break;
            case CipherAlgorithms.CAESAR:
                controller.setCipher(new CaesarService());
                break;
            default:
                JOptionPane.showMessageDialog(null,"Not found algorithm");
                break;

        }

    }


}
