package com.rabobank.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * Represents a customer entity in the banking system.
 * This class is mapped to the `Customer` table in the database and contains customer-related information such as
 * name, email, phone number, and the accounts associated with the customer.
 * 
 * The `@Data` annotation from Lombok automatically generates getters, setters, `toString()`, 
 * `equals()`, and `hashCode()` methods for the fields.
 * The `@Entity` annotation marks this class as a JPA entity, meaning it is mapped to a database table.
 */
@Data
@Entity
public class Customer {

    /**
     * The unique identifier for the customer.
     * This field represents the customer's primary key in the database, which is generated
     * automatically using the `@GeneratedValue` annotation with the `GenerationType.IDENTITY`
     * strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the customer.
     * This field stores the full name of the customer.
     */
    private String name;

    /**
     * The email address of the customer.
     * This field stores the customer's email address.
     */
    private String email;

    /**
     * The phone number of the customer.
     * This field stores the customer's phone number.
     */
    private String phoneNumber;

    /**
     * The accounts associated with this customer.
     * This is a one-to-many relationship, where one customer can have multiple accounts.
     * The `@OneToMany` annotation defines this relationship, and the `mappedBy` attribute
     * specifies that the `customer` field in the `Account` entity is the owning side.
     * The `cascade = CascadeType.ALL` ensures that operations on the customer (e.g., persist, remove) 
     * will propagate to associated accounts.
     * The `fetch = FetchType.LAZY` ensures that accounts are loaded only when accessed.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Account> accounts;
}
