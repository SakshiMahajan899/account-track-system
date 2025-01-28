package com.rabobank.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Custom exception class to represent TechnicalException errors with an error
 * code and message.
 * <p>
 * This exception is thrown when a specific TechnicalException error occurs in
 * the application. It allows the caller to capture and retrieve the error code
 * as well as a detailed error message.
 * </p>
 */
@RequiredArgsConstructor
@Data
public class TechnicalException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message
	
}
