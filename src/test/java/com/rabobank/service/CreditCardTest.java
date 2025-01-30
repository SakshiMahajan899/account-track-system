package com.rabobank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardTest {

	 private CreditCard creditCard;

	    @BeforeEach
	    public void setup() {
	        creditCard = new CreditCard();
	    }

	    @Test
	    public void testApplyCharges_shouldReturnFeeOnePerc_WhenCreditCard() {
	        BigDecimal amount = new BigDecimal("100");
	        BigDecimal expectedFee = new BigDecimal("1.00"); // 1% of 100
	        BigDecimal actualFee = creditCard.applyCharges(amount);
	        assertEquals(expectedFee, actualFee);
	    }
}
