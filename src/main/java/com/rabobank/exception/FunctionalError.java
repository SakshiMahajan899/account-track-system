package com.rabobank.exception;

/**
 * Representing error messages and their corresponding error codes.
 */
public enum FunctionalError {

	INVALID_CARD_TYPE("FUN_ERR_001", "Invalid card type"), INVALID_AMOUNT("ERR_002", "Invalid amount"),
	INVALID_ACCOUNT_NUMBER("FUN_ERR_003", "Invalid account number"), INVALID_CUSTOMER_ID("ERR_004", "Invalid customer ID"),
	INSUFFICIENT_BALANCE("FUN_ERR_005", "Insufficient funds in the account"),
	ACCOUNT_NOT_FOUND("FUN_ERR_006", "No Account Present in the database"), ILLEGAL_ARG("ERR_007", "Ilegal Argument ");

	private final String code;
	private final String errorMessage;

	/**
	 * Constructor for FunctionalErrorMessage enum.
	 *
	 * @param errorCode    the error code
	 * @param errorMessage the error message
	 */
	FunctionalError(String errorCode, String errorMessage) {
		this.code = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return code;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
}
