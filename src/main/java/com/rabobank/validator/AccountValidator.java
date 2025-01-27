package com.rabobank.validator;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rabobank.dto.AccountDto;
import com.rabobank.exception.FunctionalError;
import com.rabobank.exception.FunctionalException;
import com.rabobank.util.CardType;

/**
 * Component for validating account information.
 */
@Component
public class AccountValidator {

    /**
     * Validates the account details.
     * 
     * @param account the account model containing account details
     */
    public void validateTransferRequest(AccountDto account) {
        validateCardType(account.getCardType());       // Validate the card type
        validateAmount(account.getAmount());           // Validate the amount
        validateAccountNumber(account.getFromAccountNumber());  // Validate from account number
        validateAccountNumber(account.getToAccountNumber());  // Validate to account number

    }
    
    /**
     * Validates the account details.
     * 
     * @param account the account model containing account details
     */
    public void validateWithdrawRequest(AccountDto account) {
        validateCardType(account.getCardType());       // Validate the card type
        validateAmount(account.getAmount());           // Validate the amount
        validateAccountNumber(account.getFromAccountNumber());  // Validate from account number

    }

    /**
     * Validates the card type.
     * 
     * @param cardType the type of card used for the transaction
     * @throws FunctionalException if the card type is invalid
     */
    public void validateCardType(String cardType) {
        if (StringUtils.isBlank(cardType) || !StringUtils.equalsAny(cardType, CardType.CREDIT.name(), CardType.DEBIT.name())) {
            throw new FunctionalException(FunctionalError.INVALID_CARD_TYPE.getErrorCode(),FunctionalError.INVALID_CARD_TYPE.getErrorMessage());
        }
    }

    /**
     * Validates that the amount is greater than zero.
     * 
     * @param amount the amount to validate
     * @throws FunctionalException if the amount is null or less than or equal to zero
     */
    public void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new FunctionalException(FunctionalError.INVALID_AMOUNT.getErrorCode(),FunctionalError.INVALID_AMOUNT.getErrorMessage());
        }
    }

    /**
     * Validates that the account number is valid (non-null and positive).
     * 
     * @param accountNumber the account number to validate
     * @throws IllegalArgumentException if the account number is null or less than or equal to zero
     */
    public void validateAccountNumber(Long accountNumber) {
        if (accountNumber == null || accountNumber <= 0) {
            throw new FunctionalException(FunctionalError.INVALID_ACCOUNT_NUMBER.getErrorCode(),FunctionalError.INVALID_ACCOUNT_NUMBER.getErrorMessage());
        }
    }
}
