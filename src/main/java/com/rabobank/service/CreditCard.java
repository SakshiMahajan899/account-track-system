/**
 * 
 */
package com.rabobank.service;

import java.math.BigDecimal;

/**
 * 
 */
public class CreditCard extends PaymentMethod {

	@Override
	public BigDecimal applyCharges(BigDecimal amount) {
		return BigDecimal.valueOf(0.01).multiply(amount); // Apply 1% extra charge for credit card transactions
	}

}
