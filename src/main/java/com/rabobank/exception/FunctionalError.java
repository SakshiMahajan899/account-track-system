package com.rabobank.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Representing error messages and their corresponding error codes.
 */
@RequiredArgsConstructor
@Getter
public enum FunctionalError {

	INVALID_CARD_TYPE("FUN_ERR_001", "Invalid card type"), 
	INVALID_AMOUNT("FUN_ERR_002", "Invalid amount"),
	INVALID_ACCOUNT_NUMBER("FUN_ERR_003", "Invalid account number"), 
	INVALID_CUSTOMER_ID("FUN_ERR_004", "Invalid customer ID"),
	INSUFFICIENT_BALANCE("FUN_ERR_005", "Insufficient funds in the account"),
	ACCOUNT_NOT_FOUND("FUN_ERR_006", "No Account Present in the database"),
	ILLEGAL_ARG("FUN_ERR_007", "Ilegal Argument ");

	private final String code;
	private final String errorMessage;


	
}
