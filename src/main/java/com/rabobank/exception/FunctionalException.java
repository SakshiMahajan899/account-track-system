package com.rabobank.exception;

/**
 * Custom exception class to represent functional errors with an error code and message.
 * <p>
 * This exception is thrown when a specific functional error occurs in the application.
 * It allows the caller to capture and retrieve the error code as well as a detailed error message.
 * </p>
 */
public class FunctionalException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;

    /**
     * Constructs a new FunctionalException with the specified error code and detail message.
     * 
     * @param errorCode the error code associated with the exception.
     * @param message the detail message providing more information about the exception.
     */
    public FunctionalException(String errorCode, String message) {
        super(message); // Pass the message to the parent class (Exception)
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code associated with this exception.
     * 
     * @return the error code.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns a string representation of the exception, including the error code and message.
     * 
     * @return a string containing the error code and message.
     */
    @Override
    public String toString() {
        return "ErrorCode: " + errorCode + ", Message: " + getMessage();
    }
}
