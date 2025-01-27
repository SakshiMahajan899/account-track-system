package com.rabobank.exception;

/**
 * Custom exception class to represent functional errors with an error code and
 * message.
 * <p>
 * This exception is thrown when a specific functional error occurs in the
 * application. It allows the caller to capture and retrieve the error code as
 * well as a detailed error message.
 * </p>
 */
public class FunctionalException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String code;

	/**
	 * Constructs a new FunctionalException with the specified error code and detail
	 * message.
	 *
	 * @param code the  code associated with the error.
	 * @param message   the detail message providing more information about the
	 *                  exception.
	 */
	public FunctionalException(String code, String message) {
		super(message); // Pass the message to the parent class (Exception)
		this.code = code;
	}

	/**
	 * Gets the error code associated with this exception.
	 *
	 * @return the error code.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Returns a string representation of the exception, including the error code
	 * and message.
	 *
	 * @return a string containing the error code and message.
	 */
	@Override
	public String toString() {
		return "ErrorCode: " + code + ", Message: " + getMessage();
	}
}
