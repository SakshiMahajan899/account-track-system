/**
 *
 */
package com.rabobank.service;

import java.math.BigDecimal;

/**
 *
 */
public abstract class PaymentMethod {
	public abstract BigDecimal applyCharges(BigDecimal amount);
}
