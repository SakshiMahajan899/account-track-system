package com.rabobank.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ErrorResponse represents the structure of an error response returned by the application.
 * This class is used to encapsulate details about an error, including the error code, 
 * HTTP status, and a descriptive message.
 * 
 * The `@Data` annotation from Lombok automatically generates getters, setters,
 * toString(), equals(), and hashCode() methods for all fields.
 * The `@AllArgsConstructor` annotation generates a constructor with arguments for all fields.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    /**
     * The error code associated with the error response.
     * This can be a custom code to represent specific types of errors.
     */
    private String code;

    /**
     * The HTTP status associated with the error response.
     * This indicates the HTTP status code that reflects the error (e.g., 404 for Not Found, 500 for Internal Server Error).
     */
    private HttpStatus status;

    /**
     * A descriptive message providing details about the error.
     * This message will be shown to the user or logged for debugging purposes.
     */
    private String message;
}
