package com.rabobank.model;

import java.time.LocalDate;

import com.rabobank.util.CardType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cardNumber;
	private LocalDate expiryDate;
	@Enumerated(EnumType.STRING)
	private CardType cardType; // enum for DEBIT, CREDIT
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	public Long getId() {
		return id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public CardType getCardType() {
		return cardType;
	}

	public Account getAccount() {
		return account;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber=cardNumber;
		
	}

	public void setAccount(Account account2) {
		this.account = account2;
		
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
		
	}

}
