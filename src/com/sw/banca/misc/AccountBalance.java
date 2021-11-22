package com.sw.banca.misc;

public class AccountBalance {
    private double balanceEuro;
    private double balanceRon;

    public AccountBalance(double balanceEuro, double balanceRon){
        this.balanceEuro = balanceEuro;
        this.balanceRon = balanceRon;
    }

    public double getBalanceEuro() {
        return balanceEuro;
    }

    public void setBalanceEuro(double balanceEuro) {
        this.balanceEuro = balanceEuro;
    }

    public double getBalanceRon() {
        return balanceRon;
    }

    public void setBalanceRon(double balanceRon) {
        this.balanceRon = balanceRon;
    }
}
