package com.rabobank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabobank.exception.FunctionalError;
import com.rabobank.exception.FunctionalException;
import com.rabobank.model.Account;
import com.rabobank.repository.AccountRepository;

/**
 * Service class for managing accounts.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardFeeCalculator cardFeeCalculator;

    public AccountService(AccountRepository accountRepository, CardFeeCalculator cardFeeCalculator) {
        this.accountRepository = accountRepository;
        this.cardFeeCalculator = cardFeeCalculator;
    }

    /**
     * Gets the balance of all accounts for a given customer ID.
     * 
     * @param customerId the ID of the customer
     * @return a map containing account numbers and their balances
     * @throws FunctionalException if no accounts are found for the given customer ID
     */
    public Map<Long, BigDecimal> getBalance(Long customerId) {
        List<Account> accountList = accountRepository.findByCustomer_Id(customerId);

        if (accountList.isEmpty()) {
          throw new FunctionalException(FunctionalError.ACCOUNT_NOT_FOUND.getErrorCode(),"No accounts found for customer ID: " + customerId);
        }

        return accountList.stream()
                .collect(Collectors.toMap(Account::getAccountNumber, Account::getBalance));
    }

    /**
     * Withdraws an amount from an account using a specified card type.
     * 
     * @param accountNumber the account number
     * @param amount the amount to withdraw
     * @param cardType the type of card used for the transaction
     * @throws FunctionalException if no accounts are found for the given account number
     * @throws FunctionalException if the balance is insufficient to cover the withdrawal and fee
     */
    @Transactional
    public void withdraw(Long accountNumber, BigDecimal amount, String cardType) {
        Account account = findAccountByNumber(accountNumber);
        BigDecimal fee = cardFeeCalculator.calculateFee(cardType, amount);
        BigDecimal totalAmount = amount.add(fee);

        if (account.getBalance().compareTo(totalAmount) < 0) {
            throw new FunctionalException(FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(),FunctionalError.INSUFFICIENT_BALANCE.getErrorMessage());
            
        }

        account.setBalance(account.getBalance().subtract(totalAmount));
        accountRepository.save(account);
    }

    /**
     * Transfers an amount from one account to another using a specified card type.
     * 
     * @param fromAccountNumber the account number from which to transfer
     * @param toAccountNumber the account number to which to transfer
     * @param amount the amount to transfer
     * @param cardType the type of card used for the transaction
     * @throws FunctionalException if no accounts are found for the given account numbers
     * @throws FunctionalException if the balance is insufficient to cover the transfer and fee
     */
    @Transactional
    public void transfer(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount, String cardType) {
        Account fromAccount = findAccountByNumber(fromAccountNumber);
        Account toAccount = findAccountByNumber(toAccountNumber);
        
        BigDecimal fee = cardFeeCalculator.calculateFee(cardType, amount);
        BigDecimal totalAmount = amount.add(fee);

        if (fromAccount.getBalance().compareTo(totalAmount) < 0) {
            throw new FunctionalException(FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(),FunctionalError.INSUFFICIENT_BALANCE.getErrorMessage());
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(totalAmount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    /**
     * Finds an account by account number. Throws AccountNotFoundException if no account is found.
     * 
     * @param accountNumber the account number to find
     * @return the Account object if found
     * @throws FunctionalException if no account is found with the given number
     */
    private Account findAccountByNumber(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new FunctionalException(FunctionalError.ACCOUNT_NOT_FOUND.getErrorCode(),FunctionalError.ACCOUNT_NOT_FOUND.getErrorMessage()));
    }
}




