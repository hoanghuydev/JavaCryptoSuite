package com.raven.controller.implement;

import com.raven.form.basic.BasicCipherForm;
import com.raven.service.classical.IClassicalService;
import com.raven.service.classical.implement.PolyalphabeticService;
import com.raven.service.classical.implement.ShiftService;
import com.raven.service.classical.implement.SubstitutionService;

public class BasicCipherController {
    private boolean isEncryptMode = true;

    public BasicCipherController() {
    }

    public boolean isEncryptMode() {
        return isEncryptMode;
    }

    public void toggleMode() {
        isEncryptMode = !isEncryptMode;
    }

    public void executeOperation() {

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
