package com.rabobank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

/**
 * GlobalExceptionHandler is a centralized exception handler for the application.
 * It handles various types of exceptions thrown in the application and 
 * returns appropriate HTTP responses with error details. 
 * This class uses the {@link ControllerAdvice} annotation to handle exceptions globally.
 * 
 * It contains methods to handle specific exceptions such as:
 * - Unauthorized access
 * - Authorization denial
 * - Functional exceptions
 * - Technical exceptions
 * - General exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link Unauthorized} exceptions, which occur when a user is 
     * not authorized to access a resource.
     * 
     * @param ex The exception thrown
     * @return A ResponseEntity containing the error details and HTTP status 401 Unauthorized
     */
    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(Unauthorized ex) {
        ErrorResponse error = new ErrorResponse(TechnicalError.UNAUTHORIZED.getErrorCode(), HttpStatus.UNAUTHORIZED,
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles {@link AuthorizationDeniedException} exceptions, which occur when 
     * a user does not have the necessary permissions to access a resource.
     * 
     * @param ex The exception thrown
     * @return A ResponseEntity containing the error details and HTTP status 403 Forbidden
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ErrorResponse error = new ErrorResponse(TechnicalError.AUTHORIZATION_DENID.getErrorCode(), HttpStatus.FORBIDDEN,
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles {@link FunctionalException} exceptions, which are custom exceptions 
     * representing business logic errors.
     * 
     * @param ex The exception thrown
     * @return A ResponseEntity containing the error details and HTTP status 400 Bad Request
     */
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<ErrorResponse> handleFunctionalException(FunctionalException ex) {
        // You can access the error code and message from the exception
        ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), HttpStatus.BAD_REQUEST, ex.getMessage());

        // Returning the error response with the corresponding HTTP status
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link TechnicalException} exceptions, which are custom exceptions 
     * representing technical errors or system failures.
     * 
     * @param ex The exception thrown
     * @return A ResponseEntity containing the error details and HTTP status 500 Internal Server Error
     */
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ErrorResponse> handleTechnicalException(TechnicalException ex) {
        // You can access the error code and message from the exception
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());

        // Returning the error response with the corresponding HTTP status
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@link IllegalArgumentException} exceptions, which are thrown when
     * an illegal or inappropriate argument is passed to a method.
     * 
     * @param ex The exception thrown
     * @return A ResponseEntity containing the error details and HTTP status 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(FunctionalError.ILLEGAL_ARG.getErrorCode(), HttpStatus.BAD_REQUEST,
                ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any general {@link Exception} that is not caught by other specific exception handlers.
     * This serves as a fallback handler for unexpected errors.
     * 
     * @param ex The exception thrown
     * @return A ResponseEntity containing a generic error message and HTTP status 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(TechnicalError.EXCEPTION.getErrorCode(),
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
