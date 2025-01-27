package com.rabobank.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rabobank.model.Account;
import com.rabobank.model.Customer;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    //@Test
    public void testFindByCustomerId() {
        // Create and save a new Account instance
        Account account = new Account();
        account.setAccountNumber("1");
        account.setCustomer(new Customer());
        account.setAccountNumber("123456789");
        account.setBalance(new BigDecimal("1000.00"));
        accountRepository.save(account);

        // Retrieve the accounts by customer ID
        List<Account> accounts = accountRepository.findByCustomer_Id(any());

        // Verify the retrieved accounts
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals("123456789", accounts.get(0).getAccountNumber());
    }

    @Test
    public void testFindByCustomerId_whenNoDataMock_shouldReturnNoResults() {
        // Retrieve the accounts by a non-existing customer ID
        List<Account> accounts = accountRepository.findByCustomer_Id(999L);

        // Verify the retrieved accounts
        assertNotNull(accounts);
        assertEquals(0, accounts.size());
    }
}