package com.rabobank.dto;

import java.math.BigDecimal;

import lombok.Data;
/**
 * Data Transfer Object (DTO) representing account transaction details.
 * This DTO is used to transfer data related to a transfer or withdrawal operation,
 * including account numbers, transaction amount, and card type.
 * 
 * The `@Data` annotation from Lombok automatically generates getters, setters,
 * toString(), equals(), and hashCode() methods for all fields.
 */
@Data
public class AccountDto {

    /**
     * The account number of the sender (from which money is being transferred or withdrawn).
     */
    private String fromAccountNumber;

    /**
     * The account number of the recipient (to which money is being transferred).
     */
    private String toAccountNumber;

    /**
     * The amount of money involved in the transaction.
     * This is the value that will be transferred or withdrawn from the sender's account.
     */
    private BigDecimal amount;

    /**
     * The type of card used for the transaction (either "debit" or "credit").
     * If "credit" is selected, a 1% extra charge will be added to the transaction.
     */
    private String cardType;
}
