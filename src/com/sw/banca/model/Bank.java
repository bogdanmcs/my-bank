package com.sw.banca.model;

import com.sw.banca.misc.client.AccountBalance;
import com.sw.banca.misc.client.Transaction;
import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.model.client.Client;
import com.sw.banca.model.client.Clientable;
import com.sw.banca.model.fisc.Fiscable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Bank implements Clientable, Fiscable {
    private boolean INIT = false;

    private final List<Client> clientsList = new ArrayList<>();

    // FISC
    private final List<Client> trackedClientsList = new ArrayList<>();
    private final Map<Client, List<String>> trackedClientsOperationsMap = new HashMap<>();

    private static Bank instance;

    private Bank() {}

    public static Bank getInstance() {
        if(instance == null) {
            instance = new Bank();
        }
        return instance;
    }

    public void initialize() {
        if (!INIT) {
            INIT = true;
            createAccount(new Client(100, 1000, new AccountBalance(157, 305)));
            createAccount(new Client(200, 2000));
            createAccount(new Client(300, 3000));
        } else {
            System.out.println("Bank has already been initialized!");
        }
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
        for (Client c: clientsList) {
            if (c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()) {
                if (c.hasNoBalance()) {
                    clientsList.remove(c);
                    return ServerResponse.OPERATION_SUCCESSFUL;
                }
            }
        }
        return ServerResponse.ACCOUNT_NOT_EMPTY;
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

    @Override
    public ServerResponse withdrawCash(UserSession userSession, Transaction transaction) {
        Client currentClient = getCurrentClient(userSession);
        if (currentClient == null) {
            return ServerResponse.CLIENT_NOT_FOUND;
        }
        ServerResponse serverResponse = currentClient.setBalanceAfterWithdrawal(transaction);
        return confirmOperation(userSession, transaction, serverResponse);
    }

    @Override
    public ServerResponse depositCash(UserSession userSession, Transaction transaction) {
        Client currentClient = getCurrentClient(userSession);
        if (currentClient == null) {
            return ServerResponse.CLIENT_NOT_FOUND;
        }
        ServerResponse serverResponse = currentClient.setBalanceAfterDeposit(transaction);
        return confirmOperation(userSession, transaction, serverResponse);
    }

    @Override
    public AccountBalance getBalanceQuery(UserSession userSession) {
        for (Client c: clientsList) {
            if (c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()) {
                return new AccountBalance(c.getBalance("EURO"), c.getBalance("RON"));
            }
        }
        return new AccountBalance(-1, -1);
    }

    @Override
    public List<Client> getClientsList() {
        return clientsList;
    }

    private Client getTrackedClient(Client client) {
        for (Client c: trackedClientsList) {
            if (c.getCnp() == client.getCnp() && c.getPin() == client.getPin()) {
                return c;
            }
        }
        return null;
    }

    @Override
    public ServerResponse startClientTracking(Client client) {
        if (getTrackedClient(client) != null) {
            return ServerResponse.CLIENT_ALREADY_TRACKED;
        } else {
            trackedClientsList.add(client);
            trackedClientsOperationsMap.put(client, new ArrayList<>());
            return ServerResponse.OPERATION_SUCCESSFUL;
        }
    }

    @Override
    public ServerResponse stopClientTracking(Client client) {
        if (getTrackedClient(client) == null) {
            return ServerResponse.CLIENT_NOT_TRACKED;
        } else {
            trackedClientsOperationsMap.remove(client);
            trackedClientsList.remove(client);
            return ServerResponse.OPERATION_SUCCESSFUL;
        }
    }

    @Override
    public ServerResponse isClientTracked(Client client) {
        for (Client c: trackedClientsList) {
            if (c.getCnp() == client.getCnp() && c.getPin() == client.getPin()) {
                return ServerResponse.CLIENT_ALREADY_TRACKED;
            }
        }
        return ServerResponse.CLIENT_NOT_TRACKED;
    }

    private boolean isClientTracked(UserSession userSession) {
        for (Client c: trackedClientsList) {
            if (c.getCnp() == userSession.getCnp() && c.getPin() == userSession.getPin()) {
                return true;
            }
        }
        return false;
    }

    public List<String> getClientOperations(Client client) {
        if (isClientTracked(client) == ServerResponse.CLIENT_ALREADY_TRACKED) {
            return trackedClientsOperationsMap.get(client);
        } else {
            return new ArrayList<>();
        }
    }

    private ServerResponse confirmOperation(UserSession userSession, Transaction transaction, ServerResponse serverResponse) {
        if (serverResponse == ServerResponse.OPERATION_SUCCESSFUL) {
            if (isClientTracked(userSession)) {
                notify(userSession, transaction);
            }
        }
        return serverResponse;
    }

    @Override
    public void notify(UserSession userSession, Transaction transaction) {
        logClientTransactionInfo(userSession, transaction);
        String log = transaction.getTransactionDetails();
        Client client = getCurrentClient(userSession);
        List<String> clientOperations = trackedClientsOperationsMap.get(client);
        clientOperations.add(log);
        trackedClientsOperationsMap.put(client, clientOperations);
    }

    private void logClientTransactionInfo(UserSession userSession, Transaction transaction) {
        System.out.println("Log: client = " + userSession.getCnp() + ", operation = " + transaction.getCashOperationType() +
                ", balance = " + transaction.getBalanceType() + ", amount = " + transaction.getTotalAmount());
    }
}
