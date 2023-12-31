package com.isd.Homebanking.repositories;

import com.isd.Homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByNumber (String Number);
    Boolean existsByNumber (String Number);
    Boolean existsByActive (Boolean active);
}
