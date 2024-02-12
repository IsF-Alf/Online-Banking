package com.isd.Homebanking.services.implement;

import com.isd.Homebanking.models.Loan;
import com.isd.Homebanking.repositories.LoanRepository;
import com.isd.Homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplents implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan findLoanById(String id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Boolean existsByName(String name) {
        return loanRepository.existsByName(name);
    }
}
