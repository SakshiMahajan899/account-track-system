package com.rabobank.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * Represents a bank account entity.
 * This class is mapped to the `Account` table in the database and contains the account's
 * information such as the account number, balance, associated customer, and linked cards.
 * 
 * The `@Data` annotation from Lombok automatically generates getters, setters, 
 * `toString()`, `equals()`, and `hashCode()` methods for the fields.
 * The `@Entity` annotation marks the class as a JPA entity, meaning it will be mapped to a database table.
 */
@Data
@Entity
public class Account {

    /**
     * The unique identifier for the account.
     * This field represents the account number, which is the primary key in the database table.
     * It is marked with the `@Id` annotation to specify that it is the entity's identifier.
     */
    @Id
    private String accountNumber;

    /**
     * The balance of the account.
     * This field represents the current balance of the account and is stored as a `BigDecimal`
     * to support precise financial calculations.
     */
    private BigDecimal balance;

    /**
     * The customer associated with this account.
     * This is a many-to-one relationship, where many accounts can belong to one customer.
     * The `@ManyToOne` annotation specifies this relationship, and the `@JoinColumn` annotation
     * specifies the column (`customer_id`) that joins the `Account` and `Customer` entities.
     * The `fetch = FetchType.LAZY` means that the customer data is loaded only when accessed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * The cards linked to this account.
     * This is a one-to-many relationship, where one account can have many associated cards.
     * The `mappedBy` attribute specifies that the `account` field in the `Card` entity is the owning side.
     * The `cascade = CascadeType.ALL` means that operations like persist, merge, and remove are propagated to the associated `Card` entities.
     * The `fetch = FetchType.LAZY` ensures that the cards are loaded only when accessed.
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Card> cards;
}

