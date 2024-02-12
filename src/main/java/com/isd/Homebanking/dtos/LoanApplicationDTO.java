package com.isd.Homebanking.dtos;

public class LoanApplicationDTO {
    private String idLoan;
    private Double amount;
    private Integer payments;
    private String destinationAccount;

    public LoanApplicationDTO(String idLoan, Double amount, Integer payments, String destinationAccount) {
        this.idLoan = idLoan;
        this.amount = amount;
        this.payments = payments;
        this.destinationAccount = destinationAccount;
    }

    public String getIdLoan() {
        return idLoan;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }
}
