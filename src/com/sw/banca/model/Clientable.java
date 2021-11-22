package com.sw.banca.model;

import com.sw.banca.misc.AccountBalance;

public interface Clientable {
    void createAccount(Client client);
    void deleteAccount(UserSession userSession);
    void withdrawCash(UserSession userSession);
    void depositCash(UserSession userSession);
    AccountBalance getBalanceQuery(UserSession userSession);
}
