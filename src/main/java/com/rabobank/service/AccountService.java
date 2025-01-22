/**
 * 
 */
package com.rabobank.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.exception.AccountNotFoundException;
import com.rabobank.exception.InsufficientBalanceException;
import com.rabobank.exception.InvalidCardException;
import com.rabobank.model.Account;
import com.rabobank.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountDao;

	public BigDecimal getBalance(Long accountNumber) {
		Account account = accountDao.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
		return account.getBalance();
	}

	public void withdraw(Long accountNumber, BigDecimal amount, String cardType) {
		Account account = accountDao.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
		BigDecimal fee = calculateCardFee(cardType, amount);
		BigDecimal totalAmount = amount.add(fee);
		
		if (account.getBalance().compareTo(totalAmount) < 0) 
		{ 
			throw new InsufficientBalanceException(); 
		}
		account.setBalance(account.getBalance().subtract(totalAmount));
		accountDao.save(account);
	}



	public void transfer(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount, String cardType) {
		Account fromAccount = accountDao.findById(fromAccountNumber)
				.orElseThrow(() -> new AccountNotFoundException(fromAccountNumber));
		Account toAccount = accountDao.findById(toAccountNumber)
				.orElseThrow(() -> new AccountNotFoundException(toAccountNumber));
		BigDecimal fee = calculateCardFee(cardType, amount);
		BigDecimal totalAmount = amount.add(fee);
		
		if (fromAccount.getBalance().compareTo(totalAmount) < 0) 
		{ 
			throw new InsufficientBalanceException(); 
		}
		fromAccount.setBalance(fromAccount.getBalance().subtract(totalAmount));
		toAccount.setBalance(toAccount.getBalance().add(amount));
		accountDao.save(fromAccount);
		accountDao.save(toAccount);
	}
	
	
	/**
	 * @param amount
	 * @param account
	 * @throws InvalidCardException
	 * @throws InvalidCardException
	 * @throws InvalidCardException
	 */
	private BigDecimal calculateCardFee(String cardType,BigDecimal amount)
			throws InvalidCardException {
		if (cardType.equals("CREDIT")) {
	            CardChargeProcessor creditCard = new CreditCardChargeProcessor();
	            return creditCard.applyCharges(amount);
	        } else if (cardType.equals("DEBIT")) {
	        	CardChargeProcessor debitCard = new DebitCardChargeProcessor();
	           return debitCard.applyCharges(amount);
	        } else {
	            throw new InvalidCardException("Unknown card type: " + cardType);
	        }
	}
}
