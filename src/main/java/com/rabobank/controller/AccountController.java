/**
 * 
 */
package com.rabobank.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.model.AccountModel;
import com.rabobank.service.AccountService;
import com.rabobank.service.AuditService;
import com.rabobank.util.BankUtil;
import com.rabobank.util.Constants;
import com.rabobank.validator.AccountValidator;


@RestController
@RequestMapping(Constants.API_ACCOUNTS)
@EnableMethodSecurity
public class AccountController {

	private AccountService accountService;
	
	private final AccountValidator accountValidator;
	

    public AccountController(AccountService accountService, AccountValidator accountValidator) {
		super();
		this.accountService = accountService;
		this.accountValidator = accountValidator;
	}

    @PreAuthorize(Constants.HAS_AUTHORITY_SCOPE_USER_READ)
    @PostMapping(Constants.BALANCE)
    public ResponseEntity<BigDecimal> getBalance(@RequestBody AccountModel account) {
    	accountValidator.validateAccountNumber(account.getFromAccountNumber());
        String maskedAccountNumber = BankUtil.maskNumber(account.getFromAccountNumber());
        AuditService.logTransaction("getBalance from account " + maskedAccountNumber);
        return ResponseEntity.ok(accountService.getBalance(account.getFromAccountNumber()));
    }
    

    @PreAuthorize(Constants.HAS_AUTHORITY_SCOPE_USER_WRITE)
    @PostMapping(Constants.WITHDRAW)
    public ResponseEntity<Void> withdraw(@RequestBody AccountModel account) {
    	accountValidator.validateAccountNumber(account.getFromAccountNumber());
    	accountValidator.validateAmount(account.getAmount());
    	accountValidator.validateCardType(account.getCardType());
        accountService.withdraw(account.getFromAccountNumber(), account.getAmount(), account.getCardType());
        AuditService.logTransaction("Withdrawn " + account.getAmount() + " from account " + account.getFromAccountNumber());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(Constants.HAS_AUTHORITY_SCOPE_USER_WRITE)
    @PostMapping(Constants.TRANSFER)
    public ResponseEntity<Void> transfer(@RequestBody AccountModel account) {
    	accountValidator.validateAccountNumber(account.getFromAccountNumber());
    	accountValidator.validateAccountNumber(account.getToAccountNumber());
    	accountValidator.validateAmount(account.getAmount());
    	accountValidator.validateCardType(account.getCardType());
        accountService.transfer(account.getFromAccountNumber(),account.getToAccountNumber(), account.getAmount(), account.getCardType());
        AuditService.logTransaction("Transferred " +  account.getAmount() + " from " + account.getFromAccountNumber() + " to " + account.getToAccountNumber());
        return ResponseEntity.ok().build();
    }
}

