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
import com.rabobank.util.Constants;

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
	 * @throws FunctionalException if no accounts are found for the given customer
	 *                             ID
	 * @throws TechnicalException  if technical issue
	 */
	public Map<String, BigDecimal> getBalance(Long customerId) {
		try {
			List<Account> accountList = accountRepository.findByCustomer_Id(customerId);

			if (ObjectUtils.isEmpty(accountList)) {
				throw new FunctionalException(FunctionalError.ACCOUNT_NOT_FOUND.getErrorCode(),
						"No accounts found for customer ID: " + customerId);
			}

			return accountList.stream().collect(Collectors.toMap(Account::getAccountNumber, Account::getBalance));
		} catch (TechnicalException e) {

			throw new TechnicalException(TechnicalError.TEHNICAL_ISSUE.getErrorCode(), e.getMessage());
		}
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
	 * @throws TechnicalException  if technical issue
	 */
	@Transactional
	public void withdraw(String accountNumber, BigDecimal amount, String cardType) {
		try {
			Account account = findAccountByNumber(accountNumber);
			BigDecimal fee = cardFeeCalculator.calculateFee(cardType, amount);
			BigDecimal totalAmount = amount.add(fee);

			if (account.getBalance().compareTo(totalAmount) < 0) {
				AuditService.logTransactionError(Constants.WITHDRAW,
						String.format("%s error code during Withdrawn %s from account %s  ",
								FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(), amount, accountNumber));

				throw new FunctionalException(FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(),
						FunctionalError.INSUFFICIENT_BALANCE.getErrorMessage());

			}

			account.setBalance(account.getBalance().subtract(totalAmount));
			accountRepository.save(account);
		} catch (TechnicalException e) {
			AuditService.logTransactionError(Constants.WITHDRAW,
					String.format("%s error code during withdraw %s from account %s  ",
							TechnicalError.TEHNICAL_ISSUE.getErrorCode(), amount, accountNumber));

			throw new TechnicalException(TechnicalError.TEHNICAL_ISSUE.getErrorCode(), e.getMessage());
		}
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
		try {
			Account fromAccount = findAccountByNumber(fromAccountNumber);
			Account toAccount = findAccountByNumber(toAccountNumber);

			BigDecimal fee = cardFeeCalculator.calculateFee(cardType, amount);
			BigDecimal totalAmount = amount.add(fee);

			if (fromAccount.getBalance().compareTo(totalAmount) < 0) {
				AuditService.logTransactionError(Constants.TRANSFER,
						String.format("%s error code during Transfer %s from account %s  ",
								FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(), amount, fromAccountNumber));

				throw new FunctionalException(FunctionalError.INSUFFICIENT_BALANCE.getErrorCode(),
						FunctionalError.INSUFFICIENT_BALANCE.getErrorMessage());
			}

			fromAccount.setBalance(fromAccount.getBalance().subtract(totalAmount));
			toAccount.setBalance(toAccount.getBalance().add(amount));

			accountRepository.save(fromAccount);
			accountRepository.save(toAccount);
		} catch (TechnicalException e) {
			AuditService.logTransactionError(Constants.TRANSFER,
					String.format("%s error code during Transfer %s from account %s  ",
							TechnicalError.TEHNICAL_ISSUE.getErrorCode(), amount, fromAccountNumber));

			throw new TechnicalException(TechnicalError.TEHNICAL_ISSUE.getErrorCode(), e.getMessage());
		}
	}

	/**
	 * Finds an account by account number. Throws AccountNotFoundException if no
	 * account is found.
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
}
