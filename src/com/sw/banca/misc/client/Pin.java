package com.sw.banca.misc.client;

public class Pin {
    private String pinCode;

    public Pin(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isValid() {
        if (pinCode.length() != 4) {
            return false;
        }
        for (int i = 0; i < pinCode.length(); i++) {
            if (!Character.isDigit(pinCode.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
