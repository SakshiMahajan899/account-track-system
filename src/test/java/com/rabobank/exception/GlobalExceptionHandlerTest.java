package com.rabobank.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

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
	        assertEquals(TechnicalError.AUTHORIZATION_DENID.getErrorCode(), response.getBody().getErrorCode());
	        assertEquals("Authorization denied", response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnBadRequestError_whenFunctionalExceptionIsThrown() {
	        FunctionalException exception = new FunctionalException(FunctionalError.INVALID_CUSTOMER_ID.getErrorCode(),
	                                                                FunctionalError.INVALID_CUSTOMER_ID.getErrorMessage());
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleFunctionalException(exception);
	        
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals(FunctionalError.INVALID_CUSTOMER_ID.getErrorCode(), response.getBody().getErrorCode());
	        assertEquals(FunctionalError.INVALID_CUSTOMER_ID.getErrorMessage(), response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnInternalServerError_whenTechnicalExceptionIsThrown() {
	        TechnicalException exception = new TechnicalException(TechnicalError.EXCEPTION.getErrorCode(),
	                                                               TechnicalError.EXCEPTION.getErrorMessage());
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleFunctionalException(exception);
	        

	        assertEquals(TechnicalError.EXCEPTION.getErrorCode(), response.getBody().getErrorCode());
	        assertEquals(TechnicalError.EXCEPTION.getErrorMessage(), response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnBadRequestError_whenIllegalArgumentExceptionIsThrown() {
	        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGeneralException(exception);
	        
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals(FunctionalError.ILLEGAL_ARG.getErrorCode(), response.getBody().getErrorCode());
	        assertEquals("Invalid argument", response.getBody().getMessage());
	    }

	    @Test
	    public void shouldReturnInternalServerError_whenGeneralExceptionIsThrown() {
	        Exception exception = new Exception("General error");
	        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGeneralException(exception);
	        
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	        assertEquals(TechnicalError.EXCEPTION.getErrorCode(), response.getBody().getErrorCode());
	        assertEquals("An unexpected error occurred: General error", response.getBody().getMessage());
	    }

}
