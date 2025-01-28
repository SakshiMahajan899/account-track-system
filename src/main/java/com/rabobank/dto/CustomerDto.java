package com.rabobank.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a customer.
 * This DTO is used to transfer customer data, specifically the customer ID.
 * 
 * The `@Data` annotation from Lombok automatically generates getters, setters,
 * toString(), equals(), and hashCode() methods for all fields.
 */
@Data
public class CustomerDto {

    /**
     * The unique identifier for the customer.
     * This ID is typically used to fetch or identify a customer in the system.
     */
    private Long customerId;
}

