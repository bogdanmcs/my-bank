package com.sw.banca.misc.client;

import com.sw.banca.misc.enums.BalanceType;
import com.sw.banca.misc.enums.CashOperationType;

import java.time.LocalDateTime;

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

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getTransactionDetails() {
        return cashOperationType + " - " + balanceType + " - " +
                totalAmount + " - " + LocalDateTime.now() + "\n";
    }
}
