package com.sw.banca.model;

public class Client {
    private final int cnp;
    private int pin;
    private double soldContEuro;
    private double soldContRon;

    public Client(int cnp, int pin){
        this.cnp = cnp;
        this.pin = pin;
        soldContEuro = 0;
        soldContRon = 0;
    }

    public int getCnp() {
        return cnp;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) { this.pin = pin; }

    public double getSoldContEuro() {
        return soldContEuro;
    }

    public void setSoldContEuro(double soldContEuro) {
        this.soldContEuro = soldContEuro;
    }

    public double getSoldContRon() {
        return soldContRon;
    }

    public void setSoldContRon(double soldContRon) {
        this.soldContRon = soldContRon;
    }

    public String getInfo(){
        return "( " + cnp + ", " + pin + " )";
    }
}
