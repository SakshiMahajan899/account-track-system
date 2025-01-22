/**
 * 
 */
package com.rabobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabobank.model.Account;

/**
 * 
 */
public interface AccountRepository extends JpaRepository<Account, Long>{

}
