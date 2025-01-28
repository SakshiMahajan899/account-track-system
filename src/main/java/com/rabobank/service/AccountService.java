package com.rabobank.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabobank.exception.FunctionalError;
import com.rabobank.exception.FunctionalException;
import com.rabobank.exception.TechnicalError;
import com.rabobank.exception.TechnicalException;
import com.rabobank.model.Account;
import com.rabobank.repository.AccountRepository;
import com.rabobank.util.BankUtil;
import com.rabobank.util.Constants;

import lombok.RequiredArgsConstructor;


/**
 * Service class for managing accounts.
 * Handles balance retrieval, withdrawal, and transfer operations for customer accounts.
 */
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardFeeCalculator cardFeeCalculator;

    /**
     * Gets the balance of all accounts for a given customer ID.
     *
     * @param customerId the ID of the customer
     * @return a map containing account numbers and their balances
     * @throws FunctionalException if no accounts are found for the given customer
     *                             ID
     */
    public Map<String, BigDecimal> getBalance(Long customerId) {
        List<Account> accountList = accountRepository.findByCustomer_Id(customerId);

        if (ObjectUtils.isEmpty(accountList)) {
            throw new FunctionalException(FunctionalError.ACCOUNT_NOT_FOUND.getErrorCode(),
                    "No accounts found for customer ID: " + customerId);
        }

        return accountList.stream()
                .collect(Collectors.toMap(Account::getAccountNumber, Account::getBalance));
    }

    /**
     * Withdraws an amount from an account using a specified card type.
     *
     * @param accountNumber the account number
     * @param amount        the amount to withdraw
     * @param cardType      the type of card used for the transaction
     * @throws FunctionalException if no accounts are found for the given account
     *                             number
     * @throws FunctionalException if the balance is insufficient to cover the
     *                             withdrawal and fee
     */
    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount, String cardType) {
        Account account = findAccountByNumber(accountNumber);
        BigDecimal fee = cardFeeCalculator.calculateFee(cardType, amount);
        BigDecimal totalAmount = amount.add(fee);

        validateSufficientBalance(Constants.WITHDRAW,account, totalAmount);

        account.setBalance(account.getBalance().subtract(totalAmount));
        accountRepository.save(account);
    }

    /**
     * Transfers an amount from one account to another using a specified card type.
     *
     * @param fromAccountNumber the account number from which to transfer
     * @param toAccountNumber   the account number to which to transfer
     * @param amount            the amount to transfer
     * @param cardType          the type of card used for the transaction
     * @throws FunctionalException if no accounts are found for the given account
     *                             numbers
     * @throws FunctionalException if the balance is insufficient to cover the
     *                             transfer and fee
     * @throws TechnicalException  if technical issue
     */
    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String cardType) {
        Account fromAccount = findAccountByNumber(fromAccountNumber);
        Account toAccount = findAccountByNumber(toAccountNumber);

        BigDecimal fee = cardFeeCalculator.calculateFee(cardType, amount);
        BigDecimal totalAmount = amount.add(fee);

        validateSufficientBalance(Constants.TRANSFER,fromAccount, totalAmount);

        fromAccount.setBalance(fromAccount.getBalance().subtract(totalAmount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    /**
     * Validates if the account has sufficient balance for the withdrawal/transfer.
     *
     * @param account       the account to check
     * @param totalAmount   the total amount (including fee) to be withdrawn or transferred
     * @throws FunctionalException if the balance is insufficient
     */
    private void validateSufficientBalance(String action, Account account, BigDecimal totalAmount) {
        if (account.getBalance().compareTo(totalAmount) < 0) {
            String errorMsg = String.format("Insufficient balance. Requested: %s, Available: %s", totalAmount, account.getBalance());
            logTransactionError(action, errorMsg, account.getAccountNumber());
            throw new FunctionalException(FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(), FunctionalError.INSUFFICIENT_BALANCE.getErrorMessage());
        }
    }

    /**
     * Finds an account by account number. Throws FunctionalException if no account is found.
     *
     * @param accountNumber the account number to find
     * @return the Account object if found
     * @throws FunctionalException if no account is found with the given number
     */
    private Account findAccountByNumber(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new FunctionalException(FunctionalError.ACCOUNT_NOT_FOUND.getErrorCode(),
                        FunctionalError.ACCOUNT_NOT_FOUND.getErrorMessage()));
    }

    /**
     * Logs transaction errors for auditing purposes.
     *
     * @param action        the action being performed (e.g., withdraw, transfer)
     * @param errorMessage  the error message to log
     * @param accountNumber the account number associated with the transaction
     */
    private void logTransactionError(String action, String errorMessage, String accountNumber) {
        AuditService.logTransactionError(action,
                String.format("Timestamp: %s | %s error during %s | Account: %s | %s",
                        System.currentTimeMillis(), FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(),
                        action, BankUtil.hash(accountNumber), errorMessage));
    }
}
