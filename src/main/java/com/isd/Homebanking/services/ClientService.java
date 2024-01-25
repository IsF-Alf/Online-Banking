package com.isd.Homebanking.services;

import com.isd.Homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAllClients();
    Client findClientById (String id);
    Client findClientByEmail (String email);
    void saveClient (Client client);
    Boolean existsClientByEmail(String email);
}
