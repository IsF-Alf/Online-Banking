package com.isd.Homebanking.repositories;

import com.isd.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Client findByEmail (String Email);
    Boolean existsCientByEmail (String Email);
    Boolean existsCientById (String Id);
}
