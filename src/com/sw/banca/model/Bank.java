package com.sw.banca.model;

import com.sw.banca.misc.client.AccountBalance;
import com.sw.banca.misc.client.Transaction;
import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.model.client.Client;
import com.sw.banca.model.client.Clientable;
import com.sw.banca.model.fisc.Fiscable;

import java.util.ArrayList;
import java.util.List;

public final class Bank implements Clientable, Fiscable {
    private boolean INIT = false;

    private final List<Client> clientsList = new ArrayList<>();
    private final List<Client> trackedClientsList = new ArrayList<>();

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

    public boolean isRegistered(Client client){
        for(Client c: clientsList){
            if(c.getCnp() == client.getCnp() && c.getPin() == client.getPin()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void createAccount(Client client) {
        clientsList.add(client);
    }

    @Override
    public void deleteAccount(UserSession userSession) {
        for(Client c: clientsList){
            if(c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()){
                clientsList.remove(c);
                break;
            }
        }
    }

    private Client getCurrentClient(UserSession userSession){
        for(Client c: clientsList){
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
                if(currentBalance < transaction.getTotalAmount() || currentBalance <= 1000){
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
        return confirmOperationSuccessful();
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
        return confirmOperationSuccessful();
    }

    @Override
    public AccountBalance getBalanceQuery(UserSession userSession) {
        for(Client c: clientsList){
            if(c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()){
                return new AccountBalance(c.getEuroBalance(), c.getRonBalance());
            }
        }
        return new AccountBalance(-1, -1);
    }

    @Override
    public List<Client> getClientsList(){
        return clientsList;
    }

    private Client getTrackedClient(Client client){
        for(Client c: trackedClientsList){
            if(c.getCnp() == client.getCnp() && c.getPin() == client.getPin()){
                return c;
            }
        }
        return null;
    }

    @Override
    public ServerResponse startClientTracking(Client client) {
        if(getTrackedClient(client) != null){
            return ServerResponse.CLIENT_ALREADY_TRACKED;
        } else {
            trackedClientsList.add(client);
            return ServerResponse.OPERATION_SUCCESSFUL;
        }
    }

    @Override
    public ServerResponse stopClientTracking(Client client) {
        if(getTrackedClient(client) == null){
            return ServerResponse.CLIENT_NOT_TRACKED;
        } else {
            trackedClientsList.remove(client);
            return ServerResponse.OPERATION_SUCCESSFUL;
        }
    }

    private ServerResponse confirmOperationSuccessful() {

        notifyFisc();
        return ServerResponse.OPERATION_SUCCESSFUL;
    }

    @Override
    public ServerResponse isClientTracked(Client client) {
        for(Client c: trackedClientsList){
            if(c.getCnp() == client.getCnp() && c.getPin() == client.getPin()){
                return ServerResponse.CLIENT_ALREADY_TRACKED;
            }
        }
        return ServerResponse.CLIENT_NOT_TRACKED;
    }

    @Override
    public void notifyFisc() {

    }
}
