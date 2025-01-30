package com.rabobank.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.rabobank.exception.FunctionalError;
import com.rabobank.exception.FunctionalException;

/**
 * Service to calculate the fee for a transaction based on the card type.
 */
@Component
public class CardFeeCalculator {

	/**
	 * Calculates the fee based on the card type.
	 *
	 * @param cardType the type of card (e.g., "CREDIT", "DEBIT")
	 * @param amount   the transaction amount
	 * @return the calculated fee
	 */
	public BigDecimal calculateFee(String cardType, BigDecimal amount) {
		PaymentMethod processor = getPaymentMethod(cardType);
		return processor.applyCharges(amount);
	}

	/**
	 * Returns the appropriate PaymentMethod based on the card type.
	 *
	 * @param cardType the type of card
	 * @return the PaymentMethod for the specified card type
	 * @throws FunctionaltException if the card type is not recognized
	 */
	private PaymentMethod getPaymentMethod(String cardType) {
		switch (cardType) {
		case "CREDIT":
			return new CreditCard();
		case "DEBIT":
			return new DebitCard();
		default:
			throw new FunctionalException(FunctionalError.INVALID_CARD_TYPE.getCode(),
					FunctionalError.INVALID_CARD_TYPE.getErrorMessage() + " " + cardType);
		}
	}
}
