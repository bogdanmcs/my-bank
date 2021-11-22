package com.sw.banca.model;

import com.sw.banca.misc.AccountBalance;

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
            if(c.getCnp() == client.getCnp()){
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

    @Override
    public void withdrawCash(UserSession userSession) {

    }

    @Override
    public void depositCash(UserSession userSession) {

    }

    @Override
    public AccountBalance getBalanceQuery(UserSession userSession) {
        for(Client c: clientList){
            if(c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()){
                return new AccountBalance(c.getSoldContEuro(), c.getSoldContRon());
            }
        }
        return new AccountBalance(-1, -1);
    }
}
