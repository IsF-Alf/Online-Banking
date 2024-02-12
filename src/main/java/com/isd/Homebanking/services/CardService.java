package com.isd.Homebanking.services;

import com.isd.Homebanking.models.Card;
import com.isd.Homebanking.models.CardColor;
import com.isd.Homebanking.models.CardType;
import com.isd.Homebanking.models.Client;

import java.util.List;

public interface CardService {
    Boolean existsCardByTypeAndColorAndClientAndActive (CardType type, CardColor color, Client client, Boolean active);
    void saveCard (Card card);
    Boolean existsCardByNumber (String number);
    Card findById (String id);
    List<Card> findAll();
    Card findByNumber(String number);
    boolean existsByCvv (int cvv);
}
