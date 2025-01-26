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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Data
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;
public Long getAccountNumber() {
		return accountNumber;
	}
	//	private String accountNumber;
	private BigDecimal balance;
	@OneToOne
    @JoinColumn(name = "customer_id")
	private Customer customer_id; 
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Card> cards;
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
}
