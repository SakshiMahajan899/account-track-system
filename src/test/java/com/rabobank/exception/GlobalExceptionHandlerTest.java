package com.rabobank.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;

class GlobalExceptionHandlerTest {

	 private GlobalExceptionHandler exceptionHandler;

	    @BeforeEach
	    public void setup() {
	        exceptionHandler = new GlobalExceptionHandler();
	    }

	   

	    @Test
	    public void shouldReturnForbiddenError_whenAuthorizationDeniedExceptionIsThrown() {
	        AuthorizationDeniedException exception = new AuthorizationDeniedException("Authorization denied");
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleAuthorizationDeniedException(exception);
	        
	        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	        assertEquals(TechnicalError.AUTHORIZATION_DENID.getCode(), response.getBody().getCode());
	        assertEquals("Authorization denied", response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnBadRequestError_whenFunctionalExceptionIsThrown() {
	        FunctionalException exception = new FunctionalException(FunctionalError.INVALID_CUSTOMER_ID.getCode(),
	                                                                FunctionalError.INVALID_CUSTOMER_ID.getErrorMessage());
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleFunctionalException(exception);
	        
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals(FunctionalError.INVALID_CUSTOMER_ID.getCode(), response.getBody().getCode());
	        assertEquals(FunctionalError.INVALID_CUSTOMER_ID.getErrorMessage(), response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnInternalServerError_whenTechnicalExceptionIsThrown() {
	        FunctionalException exception = new FunctionalException(TechnicalError.EXCEPTION.getCode(),
	                                                               TechnicalError.EXCEPTION.getErrorMessage());
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleFunctionalException(exception);
	        

	        assertEquals(TechnicalError.EXCEPTION.getCode(), response.getBody().getCode());
	        assertEquals(TechnicalError.EXCEPTION.getErrorMessage(), response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnBadRequestError_whenIllegalArgumentExceptionIsThrown() {
	        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGeneralException(exception);
	        
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals(FunctionalError.ILLEGAL_ARG.getCode(), response.getBody().getCode());
	        assertEquals("Invalid argument", response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnInternalServerError_whenGeneralExceptionIsThrown() {
	        Exception exception = new Exception("General error");
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGeneralException(exception);
	        
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	        assertEquals(TechnicalError.EXCEPTION.getCode(), response.getBody().getCode());
	        assertEquals("An unexpected error occurred: General error", response.getBody().getMessage());
	    }

}
