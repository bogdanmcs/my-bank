package com.sw.banca.model.client;

import com.sw.banca.misc.AccountBalance;
import com.sw.banca.misc.ServerResponse;
import com.sw.banca.misc.Transaction;
import com.sw.banca.model.UserSession;

public interface Clientable {
    void createAccount(Client client);
    void deleteAccount(UserSession userSession);
    ServerResponse withdrawCash(UserSession userSession, Transaction transaction);
    ServerResponse depositCash(UserSession userSession, Transaction transaction);
    AccountBalance getBalanceQuery(UserSession userSession);
}
