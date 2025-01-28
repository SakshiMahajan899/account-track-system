/**
 *
 */
package com.rabobank.service;

import java.math.BigDecimal;

/**
 *Strategy defined for payment and apply charges 
 */
public abstract class PaymentMethod {
	public abstract BigDecimal applyCharges(BigDecimal amount);
}
