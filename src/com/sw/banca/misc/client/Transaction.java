package com.sw.banca.misc.client;

import com.sw.banca.misc.enums.BalanceType;
import com.sw.banca.misc.enums.CashOperationType;

public class Transaction {
    private CashOperationType cashOperationType;
    private BalanceType balanceType;
    private double totalAmount;

    public Transaction(CashOperationType cashOperationType, BalanceType balanceType, double totalAmount){
        this.cashOperationType = cashOperationType;
        this.balanceType = balanceType;
        this.totalAmount = totalAmount;
    }

    public CashOperationType getCashOperationType() {
        return cashOperationType;
    }

    public void setCashOperationType(CashOperationType cashOperationType) {
        this.cashOperationType = cashOperationType;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
