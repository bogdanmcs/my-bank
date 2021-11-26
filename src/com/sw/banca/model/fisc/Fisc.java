package com.sw.banca.model.fisc;

import com.sw.banca.misc.client.Transaction;
import com.sw.banca.model.Bank;
import com.sw.banca.model.client.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fisc {
    private static Fisc instance;

    private Fisc() {}

    public static Fisc getInstance() {
        if(instance == null) {
            instance = new Fisc();
        }
        return instance;
    }

    private boolean INIT = false;

    private final Map<Client, List<String>> trackedClientsOperationsMap = new HashMap<>();

    public void initialize(List<Client> clientsList) {
        if (!INIT) {
            INIT = true;
            for (Client c: clientsList) {
                trackedClientsOperationsMap.put(c, new ArrayList<>());
            }
        } else {
            System.out.println("Fisc has already been initialized!");
        }
    }

    public List<Client> getClientsList() {
        return Bank.getInstance().getClientsList();
    }

    private Client getCurrentClient(Client client) {
        for (Map.Entry<Client, List<String>> entry: trackedClientsOperationsMap.entrySet()) {
            if (entry.getKey().isSameClient(client)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public List<String> getClientOperations(Client client) {
        return trackedClientsOperationsMap.get(client);
    }

    public void startClientTracking(Client client) {
        trackedClientsOperationsMap.put(client, new ArrayList<>());
        Bank.getInstance().startClientTracking(client);
    }

    public void stopClientTracking(Client client) {
        Client currentClient = getCurrentClient(client);
        trackedClientsOperationsMap.remove(currentClient);
        Bank.getInstance().stopClientTracking(currentClient);
    }

    public void notifyTransaction(Client client, Transaction transaction) {
        Client currentClient = getCurrentClient(client);
        if (currentClient != null) {
            String transactionLog = transaction.getTransactionDetails();
            List<String> clientOperations = trackedClientsOperationsMap.get(client);
            clientOperations.add(transactionLog);
            trackedClientsOperationsMap.put(currentClient, clientOperations);
        }
    }

    public void notifyDeletion(Client client) {
        Client currentClient = getCurrentClient(client);
        if (currentClient != null) {
            trackedClientsOperationsMap.remove(client);
        }
    }

}
