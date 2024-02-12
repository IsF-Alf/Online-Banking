package com.isd.Homebanking.services.implement;

import com.isd.Homebanking.models.Card;
import com.isd.Homebanking.models.CardColor;
import com.isd.Homebanking.models.CardType;
import com.isd.Homebanking.models.Client;
import com.isd.Homebanking.repositories.CardRepository;
import com.isd.Homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImplements implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public Boolean existsCardByTypeAndColorAndClientAndActive(CardType type, CardColor color, Client client,
                                                              Boolean active)
    {
        return cardRepository.existsCardByTypeAndColorAndClientAndActive(type, color, client, active);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Boolean existsCardByNumber(String number) {
        return cardRepository.existsByNumber(number);
    }

    @Override
    public Card findById(String id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public boolean existsByCvv(int cvv) {
        return cardRepository.existsByCvv(cvv);
    }
}
