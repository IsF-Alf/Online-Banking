package com.isd.Homebanking.dtos;

public class LoanApplicationDTO {
    private Long idLoan;
    private Double amount;
    private Integer payments;
    private String destinationAccount;

    public LoanApplicationDTO(Long idLoan, Double amount, Integer payments, String destinationAccount) {
        this.idLoan = idLoan;
        this.amount = amount;
        this.payments = payments;
        this.destinationAccount = destinationAccount;
    }

    public Long getIdLoan() {
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
