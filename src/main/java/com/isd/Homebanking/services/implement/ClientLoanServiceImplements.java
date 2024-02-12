package com.isd.Homebanking.services.implement;

import com.isd.Homebanking.models.ClientLoan;
import com.isd.Homebanking.repositories.ClientLoanRepository;
import com.isd.Homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImplements implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public ClientLoan findById(String id) {
        return clientLoanRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean existsById(String id) {
        return clientLoanRepository.existsById(id);
    }
}
