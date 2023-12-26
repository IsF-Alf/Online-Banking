package com.isd.Homebanking.dtos;

import com.isd.Homebanking.models.Transaction;
import com.isd.Homebanking.models.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {
    private String id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDate date;
    private Double currentBalance;
    private Boolean active;

    public TransactionDTO(Transaction transaction) {
        id = transaction.getId();
        type = transaction.getType();
        amount = transaction.getAmount();
        description = transaction.getDescription();
        date = transaction.getDate();
        currentBalance = transaction.getCurrentBalance();
        active = transaction.getActive();
    }

    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public Boolean getActive() {
        return active;
    }
}
