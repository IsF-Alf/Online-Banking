package com.isd.Homebanking.repositories;

import com.isd.Homebanking.models.Card;
import com.isd.Homebanking.models.CardColor;
import com.isd.Homebanking.models.CardType;
import com.isd.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
    Boolean existsByNumber (String number);
    Boolean existsByCvv (int cvv);
    Boolean existsCardByTypeAndColorAndClientAndActive (CardType type , CardColor color, Client client, Boolean active);
    Card findByNumber(String number);
}
