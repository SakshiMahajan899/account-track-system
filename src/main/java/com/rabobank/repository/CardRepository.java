/**
 *
 */
package com.rabobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabobank.model.Card;

/**
 *
 */
public interface CardRepository extends JpaRepository<Card, Long> {

}
