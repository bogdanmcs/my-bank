package com.sw.banca.misc;

public class AccountBalance {
    private final double balanceEuro;
    private final double balanceRon;

    public AccountBalance(double balanceEuro, double balanceRon){
        this.balanceEuro = balanceEuro;
        this.balanceRon = balanceRon;
    }

    public double getSoldContEuro() {
        return balanceEuro;
    }

    public double getSoldcontRon() {
        return balanceRon;
    }
}
