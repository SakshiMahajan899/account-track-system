package com.rabobank.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing technical error codes and associated error messages.
 * 
 * This enum defines a set of predefined technical errors, each with a unique error code and message. 
 * These errors can be used throughout the application to represent different types of technical issues 
 * such as unauthorized access, authorization denial, or server errors.
 * 
 * The `@Data` annotation from Lombok automatically generates getters, setters, `toString()`, 
 * `equals()`, and `hashCode()` methods for the enum fields. 
 * The `@RequiredArgsConstructor` annotation generates a constructor with required arguments 
 * for all final fields.
 */
@RequiredArgsConstructor
@Data
public enum TechnicalError {

    /**
     * Error code for unauthorized access.
     * Represents a situation where the user does not have permission to perform an operation.
     */
    UNAUTHORIZED("SEC_001", "Invalid card type"),

    /**
     * Error code for authorization denial.
     * Represents a situation where a user does not have sufficient privileges to perform an operation.
     */
    AUTHORIZATION_DENID("SEC_002", "Invalid amount"),

    /**
     * Generic exception error code.
     * Used for unexpected or general errors within the application.
     */
    EXCEPTION("EXP_001", "Exception"),

    /**
     * Technical issue error code.
     * Represents an internal server error or technical problem.
     */
    TEHNICAL_ISSUE("TECH_001", "Internal server error");

    /**
     * The unique error code associated with the technical error.
     * Each enum constant has a specific error code that identifies the type of error.
     */
    private final String code;

    /**
     * The error message associated with the technical error.
     * Provides a description of the error that can be used for logging or displaying error details to the user.
     */
    private final String errorMessage;
}
