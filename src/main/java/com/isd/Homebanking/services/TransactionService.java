package com.isd.Homebanking.services;

import com.isd.Homebanking.models.Transaction;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
