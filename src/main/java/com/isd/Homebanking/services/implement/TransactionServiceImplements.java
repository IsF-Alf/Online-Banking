package com.isd.Homebanking.services.implement;

import com.isd.Homebanking.models.Transaction;
import com.isd.Homebanking.repositories.TransactionRepository;
import com.isd.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplements implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
