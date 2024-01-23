package com.isd.Homebanking.services;

import com.isd.Homebanking.models.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAllAccounts();
    Account findAccountById(String id);
    void saveAccount(Account account);
    Boolean existsAccountByNumber (String number);
    Account findAccountByNumber (String number);
    Account findById (String id);
    Boolean existsByActive (Boolean active);
}
