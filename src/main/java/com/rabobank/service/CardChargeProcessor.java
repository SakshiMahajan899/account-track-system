/**
 * 
 */
package com.rabobank.service;

import java.math.BigDecimal;

/**
 * 
 */
public abstract class CardChargeProcessor {
	  public abstract BigDecimal applyCharges(BigDecimal amount);
}
