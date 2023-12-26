package com.isd.Homebanking.dtos;

import com.isd.Homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private String id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;
    private Double interestRate;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments =  loan.getPayments();
        this.interestRate = loan.getInterestRate();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Double getInterestRate() {
        return interestRate;
    }
}
