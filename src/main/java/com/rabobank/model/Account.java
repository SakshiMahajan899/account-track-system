package com.rabobank.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Account {

	@Id
	private String accountNumber;
	private BigDecimal balance;
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "customer_id")
	    private Customer customer; // Updated to reflect many-to-one relationship

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Card> cards;

	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}

	/**
	 * @return
	 */
	public BigDecimal getBalance() {
		return this.balance;
	}

	/**
	 * @param subtract
	 */
	public void setBalance(BigDecimal subtract) {
		this.balance = subtract;

	}
	public void setAccountNumber(String accountNumber) {
		 this.accountNumber=accountNumber;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}
}
