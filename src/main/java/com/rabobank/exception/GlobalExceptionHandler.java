/**
 *
 */
package com.rabobank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

/**
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Unauthorized.class)
	public ResponseEntity<ErrorResponse> handleUnauthorizedException(Unauthorized ex) {
		ErrorResponse error = new ErrorResponse(TechnicalError.UNAUTHORIZED.getErrorCode(), HttpStatus.UNAUTHORIZED,
				ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
		ErrorResponse error = new ErrorResponse(TechnicalError.AUTHORIZATION_DENID.getErrorCode(), HttpStatus.FORBIDDEN,
				ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	// This method will handle the FunctionalException
	@ExceptionHandler(FunctionalException.class)
	public ResponseEntity<ErrorResponse> handleFunctionalException(FunctionalException ex) {
		// You can access the error code and message from the exception
		ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), HttpStatus.BAD_REQUEST, ex.getMessage());

		// Returning the error response with the corresponding HTTP status
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // You can adjust status as needed
	}

	// This method will handle the TechnicalException
	@ExceptionHandler(TechnicalException.class)
	public ResponseEntity<ErrorResponse> handleFunctionalException(TechnicalException ex) {
		// You can access the error code and message from the exception
		ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getMessage());

		// Returning the error response with the corresponding HTTP status
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // You can adjust status as needed
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(IllegalArgumentException ex) {
		ErrorResponse error = new ErrorResponse(FunctionalError.ILLEGAL_ARG.getErrorCode(), HttpStatus.BAD_REQUEST,
				ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		ErrorResponse error = new ErrorResponse(TechnicalError.EXCEPTION.getErrorCode(),
				HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
