package com.sw.banca.model;

import com.sw.banca.misc.*;
import com.sw.banca.model.client.Client;
import com.sw.banca.model.client.Clientable;

import java.util.ArrayList;
import java.util.List;

public final class Bank implements Clientable {
    private boolean INIT = false;
    private List<Client> clientList = new ArrayList<>();

    private static Bank instance;

    private Bank() {}

    public static Bank getInstance() {
        if(instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public void initialize() {
        if(!INIT)
        {
            INIT = true;
            createAccount(new Client(100, 1000, new AccountBalance(157, 305)));
            createAccount(new Client(200, 2000));
            createAccount(new Client(300, 3000));
        }
        else
        {
            System.out.println("Bank has already been initialized!");
        }
    }

    public List<Client> getClientList(){
        return clientList;
    }

    public boolean isRegistered(Client client){
        for(Client c: clientList){
            if(c.getCnp() == client.getCnp() && c.getPin() == client.getPin()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void createAccount(Client client) {
        clientList.add(client);
    }

    @Override
    public void deleteAccount(UserSession userSession) {
        for(Client c: clientList){
            if(c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()){
                clientList.remove(c);
                break;
            }
        }
    }

    private Client getCurrentClient(UserSession userSession){
        for(Client c: clientList){
            if(c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()){
                return c;
            }
        }
        return null;
    }

    @Override
    public ServerResponse withdrawCash(UserSession userSession, Transaction transaction) {
        Client currentClient = getCurrentClient(userSession);
        if(currentClient == null){
            return ServerResponse.CLIENT_NOT_FOUND;
        }
        double currentBalance;
        switch(transaction.getBalanceType()){
            case EURO:
                currentBalance = currentClient.getEuroBalance();
                if(currentBalance < transaction.getTotalAmount()){
                    return ServerResponse.INSUFFICIENT_FUNDS;
                } else {
                    currentClient.setEuroBalance(currentBalance - transaction.getTotalAmount());
                }
                break;
            case RON:
                currentBalance = currentClient.getRonBalance();
                if(currentBalance < transaction.getTotalAmount()){
                    return ServerResponse.INSUFFICIENT_FUNDS;
                } else {
                    currentClient.setRonBalance(currentBalance - transaction.getTotalAmount());
                }
                break;
            default:
                return ServerResponse.UNEXPECTED_ERROR;
        }
        return ServerResponse.OPERATION_SUCCESSFUL;
    }

    @Override
    public ServerResponse depositCash(UserSession userSession, Transaction transaction) {
        Client currentClient = getCurrentClient(userSession);
        if(currentClient == null){
            return ServerResponse.CLIENT_NOT_FOUND;
        }
        double currentBalance;
        switch(transaction.getBalanceType()){
            case EURO:
                currentBalance = currentClient.getEuroBalance();
                currentClient.setEuroBalance(currentBalance + transaction.getTotalAmount());
                break;
            case RON:
                currentBalance = currentClient.getRonBalance();
                currentClient.setRonBalance(currentBalance + transaction.getTotalAmount());
                break;
            default:
                return ServerResponse.UNEXPECTED_ERROR;
        }
        return ServerResponse.OPERATION_SUCCESSFUL;
    }

    @Override
    public AccountBalance getBalanceQuery(UserSession userSession) {
        for(Client c: clientList){
            if(c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()){
                return new AccountBalance(c.getEuroBalance(), c.getRonBalance());
            }
        }
        return new AccountBalance(-1, -1);
    }
}
