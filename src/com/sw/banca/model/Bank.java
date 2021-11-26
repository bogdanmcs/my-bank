package com.sw.banca.model;

import com.sw.banca.misc.client.AccountBalance;
import com.sw.banca.misc.client.Transaction;
import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.misc.enums.TrackStatus;
import com.sw.banca.model.client.Client;
import com.sw.banca.model.client.Clientable;
import com.sw.banca.model.fisc.Fiscable;

import java.util.ArrayList;
import java.util.List;

public final class Bank implements Clientable, Fiscable {
    private static Bank instance;

    private Bank() {}

    public static Bank getInstance() {
        if(instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    private boolean INIT = false;

    private final List<Client> clientsList = new ArrayList<>();

    public void initialize() {
        if (!INIT) {
            INIT = true;
            createAccount(new Client(100, 1000, new AccountBalance(709, 306.3)));
            createAccount(new Client(200, 2000));
            createAccount(new Client(300, 3000, new AccountBalance(1890, 405)));
        } else {
            System.out.println("Bank has already been initialized!");
        }
    }

    @Override
    public List<Client> getClientsList() {
        return clientsList;
    }

    private Client getCurrentClient(UserSession userSession) {
        for (Client c: clientsList) {
            if (c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()) {
                return c;
            }
        }
        return null;
    }

    private Client getCurrentClient(Client client) {
        for (Client c: clientsList) {
            if (c.getCnp() == client.getCnp() && c.getPin() == client.getPin()) {
                return c;
            }
        }
        return null;
    }

    private boolean isRegistered(Client client) {
        for (Client c: clientsList) {
            if (c.getCnp() == client.getCnp() && c.getPin() == client.getPin()) {
                return true;
            }
        }
        return false;
    }

    public ServerResponse validateCredentials(Client client) {
        Client currentClient = getCurrentClient(client);
        if (currentClient != null) {
            return ServerResponse.OPERATION_SUCCESSFUL;
        } else {
            return ServerResponse.CLIENT_NOT_FOUND;
        }
    }

    @Override
    public ServerResponse createAccount(Client client) {
        if (isRegistered(client)) {
            return ServerResponse.CLIENT_ALREADY_EXISTS;
        } else {
            clientsList.add(client);
            return ServerResponse.OPERATION_SUCCESSFUL;
        }
    }

    @Override
    public ServerResponse deleteAccount(UserSession userSession) {
        Client currentClient = getCurrentClient(userSession);
        if (currentClient == null) {
            return ServerResponse.CLIENT_NOT_FOUND;
        } else if (currentClient.hasNoBalance()) {
            clientsList.remove(currentClient);
            return ServerResponse.OPERATION_SUCCESSFUL;
        } else {
            return ServerResponse.ACCOUNT_NOT_EMPTY;
        }
    }

    @Override
    public ServerResponse withdrawCash(UserSession userSession, Transaction transaction) {
        Client currentClient = getCurrentClient(userSession);
        if (currentClient == null) {
            return ServerResponse.CLIENT_NOT_FOUND;
        }
        ServerResponse serverResponse = currentClient.setBalanceAfterWithdrawal(transaction);
        return confirmOperation(currentClient, transaction, serverResponse);
    }

    @Override
    public ServerResponse depositCash(UserSession userSession, Transaction transaction) {
        Client currentClient = getCurrentClient(userSession);
        if (currentClient == null) {
            return ServerResponse.CLIENT_NOT_FOUND;
        }
        ServerResponse serverResponse = currentClient.setBalanceAfterDeposit(transaction);
        return confirmOperation(currentClient, transaction, serverResponse);
    }

    @Override
    public AccountBalance getBalanceQuery(UserSession userSession) {
        Client currentClient = getCurrentClient(userSession);
        if (currentClient == null) {
            return new AccountBalance(-1, -1);
        } else {
            return new AccountBalance(currentClient.getBalance("EURO"), currentClient.getBalance("RON"));
        }
    }

    @Override
    public ServerResponse startClientTracking(Client client) {
        Client currentClient = getCurrentClient(client);
        if (currentClient == null) {
            return ServerResponse.CLIENT_NOT_FOUND;
        } else if (currentClient.isTracked()) {
            return ServerResponse.CLIENT_ALREADY_TRACKED;
        } else {
            currentClient.setTrackStatus(TrackStatus.TRACKED);
            return ServerResponse.OPERATION_SUCCESSFUL;
        }
    }

    @Override
    public ServerResponse stopClientTracking(Client client) {
        Client currentClient = getCurrentClient(client);
        if (currentClient == null) {
            return ServerResponse.CLIENT_NOT_FOUND;
        } else if (currentClient.isTracked()) {
            currentClient.setTrackStatus(TrackStatus.NOT_TRACKED);
            return ServerResponse.OPERATION_SUCCESSFUL;
        } else {
            return ServerResponse.CLIENT_ALREADY_TRACKED;
        }
    }

    private ServerResponse confirmOperation(Client client, Transaction transaction, ServerResponse serverResponse) {
        if (serverResponse == ServerResponse.OPERATION_SUCCESSFUL && client.isTracked()) {
                notify(client, transaction);
        }
        return serverResponse;
    }


    private void notify(Client client, Transaction transaction) {
        logClientTransactionInfo(client, transaction);
        String log = transaction.getTransactionDetails();
        client.addOperation(log);
    }

    private void logClientTransactionInfo(Client client, Transaction transaction) {
        System.out.println("Log: client = " + client.getCnp() + ", operation = " + transaction.getCashOperationType() +
                ", balance = " + transaction.getBalanceType() + ", amount = " + transaction.getTotalAmount());
    }
}