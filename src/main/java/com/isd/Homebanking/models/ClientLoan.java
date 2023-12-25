package com.isd.Homebanking.models;

import jakarta.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double amount;
    private Integer payment;
    private Double currentAmount;
    private Integer currentPayments;
    @ManyToOne()
    private Client client;
    @ManyToOne()
    private Loan loan;

    public ClientLoan() {
    }

    public ClientLoan(Double amount, Integer payment, Double currentAmount, Integer currentPayments, Client client,
                      Loan loan)
    {
        this.amount = amount;
        this.payment = payment;
        this.currentAmount = currentAmount;
        this.currentPayments = currentPayments;
        this.client = client;
        this.loan = loan;
    }

    public String getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Integer getCurrentPayments() {
        return currentPayments;
    }

    public void setCurrentPayments(Integer currentPayments) {
        this.currentPayments = currentPayments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
