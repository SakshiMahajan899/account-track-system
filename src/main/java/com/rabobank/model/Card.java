package com.rabobank.model;

import java.time.LocalDate;

import com.rabobank.util.CardType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Represents a bank card entity associated with an account.
 * This class is mapped to the `Card` table in the database and contains information
 * related to a bank card, such as the card number, expiry date, card type, and the
 * associated account.
 * 
 * The `@Data` annotation from Lombok automatically generates getters, setters, 
 * `toString()`, `equals()`, and `hashCode()` methods for the fields.
 * The `@Entity` annotation marks the class as a JPA entity, meaning it will be mapped to a database table.
 */
@Data
@Entity
public class Card {

    /**
     * The unique identifier for the card.
     * This field represents the card's primary key in the database, which is generated
     * automatically using the `@GeneratedValue` annotation with the `GenerationType.IDENTITY`
     * strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The card number associated with the bank card.
     * This field stores the unique card number that identifies the card in the system.
     */
    private String cardNumber;

    /**
     * The expiry date of the card.
     * This field stores the expiration date of the card, typically in `MM/YYYY` format.
     */
    private LocalDate expiryDate;

    /**
     * The type of the card (e.g., DEBIT, CREDIT).
     * This field uses the `@Enumerated(EnumType.STRING)` annotation to store the card type
     * as a string value (e.g., "DEBIT", "CREDIT") in the database.
     */
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    /**
     * The account associated with this card.
     * This is a many-to-one relationship, where many cards can be associated with a single account.
     * The `@ManyToOne` annotation defines this relationship, and the `@JoinColumn` annotation
     * specifies the foreign key column (`account_id`) in the `Card` table that links to the `Account` entity.
     * The `nullable = false` ensures that every card must be linked to an account.
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}

