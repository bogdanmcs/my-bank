package com.sw.banca.misc.client;

public class AccountBalance {
    private final double balanceEuro;
    private final double balanceRon;

    public AccountBalance(double balanceEuro, double balanceRon) {
        this.balanceEuro = balanceEuro;
        this.balanceRon = balanceRon;
    }

    public double getBalanceEuro() {
        return balanceEuro;
    }

    public double getBalanceRon() {
        return balanceRon;
    }
}
