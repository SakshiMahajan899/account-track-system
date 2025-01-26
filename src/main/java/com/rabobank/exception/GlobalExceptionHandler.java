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
	    public ResponseEntity<Error> handleUnauthorizedException(Unauthorized ex) {
	        Error error = new Error(401, HttpStatus.UNAUTHORIZED, ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Error> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        Error error = new Error(403, HttpStatus.FORBIDDEN, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Error> handleInsufficientBalance(InsufficientBalanceException ex) {
        Error error = new Error(400, HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Error> handleAccountNotFound(AccountNotFoundException ex) {
        Error error = new Error(404, HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<Error> handleInvalidCard(InvalidCardException ex) {
        Error error = new Error(400, HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGeneralException(Exception ex) {
        Error error = new Error(500, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
