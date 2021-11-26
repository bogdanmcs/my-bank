package com.sw.banca.model.client;

import com.sw.banca.misc.client.AccountBalance;
import com.sw.banca.misc.client.Transaction;
import com.sw.banca.misc.enums.BalanceType;
import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.misc.enums.TrackStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    private final int cnp;
    private final int pin;
    private final Map<String, Double> accountBalancesMap = new HashMap<>();
    private TrackStatus trackStatus;
    private List<String> operationsList = new ArrayList<>();

    public Client(int cnp, int pin) {
        this.cnp = cnp;
        this.pin = pin;
        for (BalanceType balanceType: BalanceType.values()) {
            accountBalancesMap.put(balanceType.toString(), 0.0);
        }
        trackStatus = TrackStatus.NOT_TRACKED;
    }

    public Client(int cnp, int pin, AccountBalance accountBalance) {
        this.cnp = cnp;
        this.pin = pin;
        accountBalancesMap.put("EURO", accountBalance.getBalanceEuro());
        accountBalancesMap.put("RON", accountBalance.getBalanceRon());
        trackStatus = TrackStatus.NOT_TRACKED;
    }

    public int getCnp() {
        return cnp;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance(String currency) {
        return accountBalancesMap.get(currency);
    }

    public List<String> getOperationsList() {
        return operationsList;
    }

    public void addOperation(String operation) {
        operationsList.add(operation);
    }

    public void clearOperationsList() {
        operationsList = new ArrayList<>();
    }

    public boolean isTracked() {
        return (trackStatus == TrackStatus.TRACKED);
    }

    public void setTrackStatus(TrackStatus trackStatus) {
        this.trackStatus = trackStatus;
        if (trackStatus == TrackStatus.NOT_TRACKED) {
            clearOperationsList();
        }
    }

    public ServerResponse setBalanceAfterWithdrawal(Transaction transaction) {
        String balanceType = transaction.getBalanceType().toString();
        Double amount = accountBalancesMap.get(balanceType);
        double transactionResult = amount - transaction.getTotalAmount();
        if (transactionResult < 0) {
            return ServerResponse.INSUFFICIENT_FUNDS;
        } else {
            accountBalancesMap.put(balanceType, transactionResult);
            return ServerResponse.OPERATION_SUCCESSFUL;
        }
    }

    public ServerResponse setBalanceAfterDeposit(Transaction transaction) {
        String balanceType = transaction.getBalanceType().toString();
        Double amount = accountBalancesMap.get(balanceType);
        double transactionResult = amount + transaction.getTotalAmount();
        accountBalancesMap.put(balanceType, transactionResult);
        return ServerResponse.OPERATION_SUCCESSFUL;
    }

    public boolean hasNoBalance(){
        for (Map.Entry<String, Double> entry: accountBalancesMap.entrySet()) {
            if (entry.getValue() != 0) {
                return false;
            }
        }
        return true;
    }

    public String getInfo() {
        return "( id: " + cnp + ", EURO: " + accountBalancesMap.get("EURO") + ", RON: " + accountBalancesMap.get("RON") + " )";
    }
}
