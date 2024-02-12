package com.isd.Homebanking.services;

import com.isd.Homebanking.models.ClientLoan;

public interface ClientLoanService {
    void saveClientLoan(ClientLoan clientLoan);
    ClientLoan findById (String id);
    Boolean existsById (String id);
}
