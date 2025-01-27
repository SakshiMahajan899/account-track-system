package com.rabobank.exception;

public enum TechnicalError {
	    UNAUTHORIZED("SEC_001", "Invalid card type"),
	    AUTHORIZATION_DENID("SEC_002", "Invalid amount"),
	    EXCEPTION("ERR003", "Invalid account number");

	    private final String errorCode;
	    private final String errorMessage;

	    /**
	     * Constructor for FunctionalErrorMessage enum.
	     * 
	     * @param errorCode the error code
	     * @param errorMessage the error message
	     */
	    TechnicalError(String errorCode, String errorMessage) {
	        this.errorCode = errorCode;
	        this.errorMessage = errorMessage;
	    }

	    /**
	     * Gets the error code.
	     * 
	     * @return the error code
	     */
	    public String getErrorCode() {
	        return errorCode;
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
