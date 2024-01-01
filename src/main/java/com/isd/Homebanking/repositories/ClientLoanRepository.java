package com.isd.Homebanking.repositories;

import com.isd.Homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientLoanRepository extends JpaRepository<ClientLoan, String> {
}
