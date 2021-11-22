package com.sw.banca.model;

import com.sw.banca.misc.AccountBalance;

public class Client {
    private final int cnp;
    private final int pin;
    private final AccountBalance accountBalance;

    public Client(int cnp, int pin){
        this.cnp = cnp;
        this.pin = pin;
        accountBalance = new AccountBalance(0, 0);
    }

    public int getCnp() {
        return cnp;
    }

    public int getPin() {
        return pin;
    }

    public double getSoldContEuro() {
        return accountBalance.getSoldContEuro();
    }

    public double getSoldContRon() {
        return accountBalance.getSoldcontRon();
    }

    public String getInfo(){
        return "( " + cnp + ", " + pin + " )";
    }
}
