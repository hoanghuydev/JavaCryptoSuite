package com.raven.controller.implement;

import com.raven.form.basic.BasicCipherForm;
import com.raven.service.classical.IClassicalService;
import com.raven.service.classical.implement.PolyalphabeticService;
import com.raven.service.classical.implement.ShiftService;
import com.raven.service.classical.implement.SubstitutionService;

public class BasicCipherController {
    private BasicCipherForm view;
    private boolean isEncryptMode = true;

    public BasicCipherController(BasicCipherForm view) {
        this.view = view;
    }

    public boolean isEncryptMode() {
        return isEncryptMode;
    }

    public void toggleMode() {
        isEncryptMode = !isEncryptMode;
    }

    public void executeOperation() {
        String algorithm = view.getSelectedAlgorithm();
        String inputText = view.getInputText();
        String result;

        IClassicalService cipherService = getCipherService(algorithm);
        if (cipherService == null) {
            result = "Selected algorithm not implemented.";
        } else {
            if (isEncryptMode) {
                result = cipherService.encrypt(inputText);
            } else {
                result = cipherService.decrypt(inputText);
            }
        }

        view.setOutputText(result);
    }

    private IClassicalService getCipherService(String algorithm) {
        switch (algorithm) {
            case "Polyalphabetic Cipher":
                return new PolyalphabeticService();
            case "Caesar Shift Cipher":
                return new ShiftService();
            case "Substitution Cipher":
                return new SubstitutionService();
            default:
                return null;
        }
    }
}
