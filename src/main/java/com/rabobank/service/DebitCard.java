/**
 *
 */
package com.rabobank.service;

import java.math.BigDecimal;


public class DebitCard extends PaymentMethod {

	@Override
	public BigDecimal applyCharges(BigDecimal amount) {
		return BigDecimal.ZERO; // No extra charges for debit card
	}

}
