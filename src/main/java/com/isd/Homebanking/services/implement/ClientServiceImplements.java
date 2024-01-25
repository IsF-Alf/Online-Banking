package com.isd.Homebanking.services.implement;

import com.isd.Homebanking.models.Client;
import com.isd.Homebanking.repositories.ClientRepository;
import com.isd.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImplements implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(String id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Boolean existsClientByEmail(String email) {
        return clientRepository.existsCientByEmail(email);
    }
}
