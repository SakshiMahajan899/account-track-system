package com.rabobank.exception;

public enum TechnicalError {
	UNAUTHORIZED("SEC_001", "Invalid card type"), AUTHORIZATION_DENID("SEC_002", "Invalid amount"),
	EXCEPTION("EXP_001", "Exception"), TEHNICAL_ISSUE("TECH_001", "Internal server error");

	private final String code;
	private final String errorMessage;

	/**
	 * Constructor for FunctionalErrorMessage enum.
	 *
	 * @param errorCode    the error code
	 * @param errorMessage the error message
	 */
	TechnicalError(String errorCode, String errorMessage) {
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
