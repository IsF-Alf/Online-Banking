package com.isd.Homebanking.dtos;

import com.isd.Homebanking.models.Card;
import com.isd.Homebanking.models.CardColor;
import com.isd.Homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private String id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private Boolean active;

    public CardDTO(Card card) {
        id = card.getId();
        cardHolder = card.getCardHolder();
        type = card.getType();
        color = card.getColor();
        number = card.getNumber();
        cvv = card.getCvv();
        thruDate = card.getThruDate();
        fromDate = card.getFromDate();
        active = card.getActive();
    }

    public String getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Boolean getActive() {
        return active;
    }
}
