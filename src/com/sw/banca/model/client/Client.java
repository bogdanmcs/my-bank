package com.sw.banca.model.client;

import com.sw.banca.misc.client.AccountBalance;

public class Client {
    private final int cnp;
    private final int pin;
    private final AccountBalance accountBalance;

    public Client(int cnp, int pin) {
        this.cnp = cnp;
        this.pin = pin;
        accountBalance = new AccountBalance(0, 0);
    }

    public Client(int cnp, int pin, AccountBalance accountBalance) {
        this.cnp = cnp;
        this.pin = pin;
        this.accountBalance = accountBalance;
    }

    public int getCnp() {
        return cnp;
    }

    public int getPin() {
        return pin;
    }

    public double getEuroBalance() {
        return accountBalance.getBalanceEuro();
    }

    public double getRonBalance() {
        return accountBalance.getBalanceRon();
    }

    public void setEuroBalance(double euroAmount) {
        accountBalance.setBalanceEuro(euroAmount);
    }

    public void setRonBalance(double ronAmount) {
        accountBalance.setBalanceRon(ronAmount);
    }

    public String getInfo() {
        return "( id: " + cnp + ", EURO: " + accountBalance.getBalanceEuro() + ", RON: " + accountBalance.getBalanceRon() + " )";
    }
}
