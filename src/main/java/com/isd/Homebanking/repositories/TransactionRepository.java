package com.isd.Homebanking.repositories;

import com.isd.Homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
