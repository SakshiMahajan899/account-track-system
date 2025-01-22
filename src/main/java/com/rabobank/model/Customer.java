package com.rabobank.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Customer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private String name; 
	private String email;
	private String phoneNumber;
	@OneToOne(mappedBy = "customer_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;
}
