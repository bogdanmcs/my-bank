package com.sw.banca.model;

public final class UserSession {

    private static UserSession instance;

    private UserSession() {}

    public static UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    private int cnp;
    private int pin;

    public int getCnp() {
        return cnp;
    }

    public void setCnp(int cnp) {
        this.cnp = cnp;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setCnpAndPin(int cnp, int pin) {
        this.cnp = cnp;
        this.pin = pin;
    }

    public void clearSession() {
        cnp = -1;
        pin = -1;
    }
}
