package com.rabobank.validator;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rabobank.exception.InvalidCardException;
import com.rabobank.model.AccountModel;
import com.rabobank.util.CardType;

@Component
public class AccountValidator {
	
	public void validateAccount(AccountModel account) {
        validateCardType(account.getCardType());       // Validate the card type
        validateAmount(account.getAmount());           // Validate the amount
        validateAccountNumber(account.getFromAccountNumber());  // Validate from account number
        validateAccountNumber(account.getToAccountNumber());  // Validate from account number
      
        // Add more validations as needed
    }

	public void validateCardType( String cardType) {
		if (StringUtils.isBlank(cardType)|| !StringUtils.equalsAny(cardType, CardType.CREDIT.name(),CardType.DEBIT.name())) {
            throw new InvalidCardException("Invalid card type ");
        }
	}
	
	  // Validates that the account amount is greater than zero
	public void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    // Validates that the from account number is valid (non-null and positive)
	public void validateAccountNumber(Long accountNumber) {
        if (accountNumber == null || accountNumber <= 0) {
            throw new IllegalArgumentException("Invalid from account number");
        }
    }

	
	

}
