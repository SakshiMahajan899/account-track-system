package com.rabobank.dto;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class AccountDto {
	
	private Long fromAccountNumber;
	
    private Long toAccountNumber;
    private BigDecimal amount;
    private String cardType;

    // Getters and setters
    public Long getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(Long fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public Long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(Long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
