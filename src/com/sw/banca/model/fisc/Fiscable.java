package com.sw.banca.model.fisc;

import com.sw.banca.misc.enums.ServerResponse;
import com.sw.banca.model.client.Client;

import java.util.List;

public interface Fiscable {
    ServerResponse startClientTracking(Client client);
    ServerResponse stopClientTracking(Client client);
    ServerResponse isClientTracked(Client client);
    List<Client> getClientsList();
//    List<Client> getTrackedClients();
    void notifyFisc();
//    void viewTrackedClientStatusChanged(Client client);
}
