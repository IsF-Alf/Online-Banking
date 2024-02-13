package com.isd.Homebanking.repositories;

import com.isd.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, String> {
    Optional<Loan> findById (String id);
    Boolean existsByName (String name);
}
