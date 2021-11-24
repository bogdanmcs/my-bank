package com.sw.banca.misc.client;

import com.sw.banca.misc.enums.BalanceType;
import com.sw.banca.misc.enums.CashOperationType;

import java.time.LocalDateTime;

public class Transaction {
    private final CashOperationType cashOperationType;
    private final BalanceType balanceType;
    private final double totalAmount;

    public Transaction(CashOperationType cashOperationType, BalanceType balanceType, double totalAmount) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getTransactionDetails() {
        return cashOperationType + " - " + balanceType + " - " +
                totalAmount + " - " + LocalDateTime.now() + "\n";
    }
}
