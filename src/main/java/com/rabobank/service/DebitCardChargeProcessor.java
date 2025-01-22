/**
 * 
 */
package com.rabobank.service;

import java.math.BigDecimal;

/**
 * 
 */
public class DebitCardChargeProcessor extends CardChargeProcessor {

	@Override
	public BigDecimal applyCharges(BigDecimal amount) {
		return BigDecimal.ZERO; // No extra charges for debit card
	}

}
