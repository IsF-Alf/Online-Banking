package com.isd.Homebanking.repositories;

import com.isd.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, String> {
    Loan findById (long id);
    Boolean existsByName (String name);
}
