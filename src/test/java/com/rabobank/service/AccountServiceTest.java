package com.rabobank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.rabobank.exception.FunctionalException;
import com.rabobank.exception.TechnicalException;
import com.rabobank.model.Account;
import com.rabobank.model.Card;
import com.rabobank.repository.AccountRepository;
import com.rabobank.util.CardType;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CardFeeCalculator cardFeeCalculator;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository, cardFeeCalculator);

        // Sample mock setup for findById and findByCustomer_Id
        account = new Account();
        account.setAccountNumber("1");
        account.setBalance(BigDecimal.valueOf(5000L));
        Set<Card> cardSet = new HashSet<>();

        // Add cards to the set
        Card card1 = new Card();
        card1.setCardNumber("1234 5678 9876 5432");
        card1.setCardType(CardType.DEBIT);
        // Assuming Account object exists
        card1.setAccount(account);

        Card card2 = new Card();
        card2.setCardNumber("2345 6789 8765 4321");
        card2.setCardType(CardType.CREDIT);
        card2.setAccount(account);

        cardSet.add(card1);
        cardSet.add(card2);
        when(accountRepository.findById("1")).thenReturn(Optional.of(account));
        when(accountRepository.findByCustomer_Id(123L)).thenReturn(List.of(account));
    }

    @Test
    void testGetBalance_Success() {
        
        when(accountRepository.findByCustomer_Id(any())).thenReturn(Collections.singletonList(account));
        // Act
        Map<String, BigDecimal> balance = accountService.getBalance(1l);

        // Assert
        assertNotNull(balance);
    }

    @Test
    void testGetBalance_shoulReturnAccountNotFound_whenNoAccountExistInDb() {
        when(accountRepository.findByCustomer_Id(any())).thenReturn(Collections.emptyList());

        // Act & Assert
        FunctionalException exception = assertThrows(FunctionalException.class, () -> accountService.getBalance(1l));
        assertEquals("No accounts found for customer ID: 1", exception.getMessage());
    }

    @Test
    void testWithdraw_shouldSuccess_whenAmountIsAvailableForOp() {
        // Arrange
        String accountNumber = "12345";
        BigDecimal amount = BigDecimal.valueOf(200);
        String cardType = "DEBIT";
        BigDecimal fee = BigDecimal.valueOf(10); // Example fee
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(cardFeeCalculator.calculateFee(cardType, amount)).thenReturn(fee);

        // Act
        accountService.withdraw(accountNumber, amount, cardType);

        // Assert
        assertEquals(BigDecimal.valueOf(4790), account.getBalance()); // 1000 - 200 - 10
        verify(accountRepository).save(account);
    }

    @Test
    void testWithdraw_shouldThrowInsufficientBalance_whenAmountIsLessInDb() {
        // Arrange
        String accountNumber = "12345";
        BigDecimal amount = BigDecimal.valueOf(7200); // Exceeding balance
        String cardType = "DEBIT";
        BigDecimal fee = BigDecimal.valueOf(10); // Example fee
        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));
        when(cardFeeCalculator.calculateFee(cardType, amount)).thenReturn(fee);

        // Act & Assert
        FunctionalException exception = assertThrows(FunctionalException.class, () -> accountService.withdraw(accountNumber, amount, cardType));
        assertEquals("FUN_ERR_005", exception.getCode());
    }

    @Test
    void testWithdraw_TechnicalException() {
        // Arrange
        String accountNumber = "12345";
        BigDecimal amount = BigDecimal.valueOf(200);
        String cardType = "DEBIT";
        when(accountRepository.findById(accountNumber)).thenThrow(new TechnicalException("Database error"));

        // Act & Assert
        TechnicalException exception = assertThrows(TechnicalException.class, () -> accountService.withdraw(accountNumber, amount, cardType));
        assertTrue(exception.getMessage().contains("Database error"));
    }

    @Test
    void testTransfer_shouldReturnok_whenAmountIsPresentForAccount() {
        // Arrange
        String fromAccountNumber = "1";
        String toAccountNumber = "2";
        BigDecimal amount = BigDecimal.valueOf(200);
        String cardType = "DEBIT";
        BigDecimal fee = BigDecimal.valueOf(10); // Example fee
        Account toAccount = new Account(); // Example recipient account
        toAccount.setBalance(BigDecimal.valueOf(500));
        toAccount.setAccountNumber("67890");
        when(accountRepository.findById("1")).thenReturn(Optional.of(account));
        when(accountRepository.findById("2")).thenReturn(Optional.of(toAccount));
        when(cardFeeCalculator.calculateFee(cardType, amount)).thenReturn(fee);

        // Act
        accountService.transfer(fromAccountNumber, toAccountNumber, amount, cardType);

        // Assert
        assertEquals(BigDecimal.valueOf(4790), account.getBalance()); 
        assertEquals(BigDecimal.valueOf(700), toAccount.getBalance()); 
        verify(accountRepository).save(account);
        verify(accountRepository).save(toAccount);
    }

    @Test
    void testTransfer_shouldReturnException_whenInsufficientBalance() {
        // Arrange
        String fromAccountNumber = "1";
        String toAccountNumber = "2";
        BigDecimal amount = BigDecimal.valueOf(12200); // Exceeding balance
        String cardType = "DEBIT";
        BigDecimal fee = BigDecimal.valueOf(10); // Example fee
        Account toAccount = new Account(); // Example recipient account
        toAccount.setBalance(BigDecimal.valueOf(500));
        toAccount.setAccountNumber("67890");
        when(accountRepository.findById(fromAccountNumber)).thenReturn(Optional.of(account));
        when(accountRepository.findById(toAccountNumber)).thenReturn(Optional.of(toAccount));
        when(cardFeeCalculator.calculateFee(cardType, amount)).thenReturn(fee);

        // Act & Assert
        FunctionalException exception = assertThrows(FunctionalException.class, () -> accountService.transfer(fromAccountNumber, toAccountNumber, amount, cardType));
        assertEquals("FUN_ERR_005", exception.getCode());
    }

    @Test
    void testTransfer_TechnicalException() {
        // Arrange
        String fromAccountNumber = "12345";
        String toAccountNumber = "67890";
        BigDecimal amount = BigDecimal.valueOf(200);
        String cardType = "DEBIT";
        when(accountRepository.findById(fromAccountNumber)).thenThrow(new TechnicalException("Database error"));

        // Act & Assert
        TechnicalException exception = assertThrows(TechnicalException.class, () -> accountService.transfer(fromAccountNumber, toAccountNumber, amount, cardType));
        assertTrue(exception.getMessage().contains("Database error"));
    }
}
