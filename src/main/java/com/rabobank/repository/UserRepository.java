/**
 *
 */
package com.rabobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabobank.model.Card;

/**
 *
 */
public interface UserRepository extends JpaRepository<Card, Long> {

}
