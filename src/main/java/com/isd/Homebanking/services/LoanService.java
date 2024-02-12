package com.isd.Homebanking.services;

import com.isd.Homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> findAllLoans();
    Loan findLoanById(String id);
    void saveLoan (Loan loan);
    Boolean existsByName (String name);
}
