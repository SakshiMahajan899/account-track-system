package com.rabobank.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.dto.AccountDto;
import com.rabobank.dto.CustomerDto;
import com.rabobank.dto.ResponseDto;
import com.rabobank.service.AccountService;
import com.rabobank.service.AuditService;
import com.rabobank.util.Constants;
import com.rabobank.validator.AccountValidator;
import com.rabobank.validator.CustomerValidator;

/**
 * REST controller for managing account operations.
 */
@RestController
@RequestMapping(Constants.API_ACCOUNTS)
@EnableMethodSecurity
public class AccountController {

    private final AccountService accountService;
    private final AccountValidator accountValidator;
    private final CustomerValidator customerValidator;

    /**
     * Constructor to initialize AccountController with necessary dependencies.
     * 
     * @param accountService the account service to handle business logic
     */
    public AccountController(AccountService accountService,AccountValidator accountValidator,CustomerValidator customerValidator) {
        this.accountService = accountService;
        this.accountValidator = accountValidator;
        this.customerValidator = customerValidator;
    }
    
    /**
     * Operation to get the balance for all accounts of a given customer.
     * 
     * @param customerModel the model containing customer information
     * @return a map containing account numbers and their balances
     */
    @PreAuthorize(Constants.HAS_AUTHORITY_SCOPE_USER_READ)
    @PostMapping(Constants.BALANCE)
    public Map<Long, BigDecimal> getBalance(@RequestBody CustomerDto customerDto) {
    	customerValidator.validateCustomerId(customerDto.getCustomerId());
        return accountService.getBalance(customerDto.getCustomerId());
    }

    /**
     * Operation to withdraw an amount from an account using a specified card type.
     * 
     * @param account the model containing account information for withdrawal
     * @return a response entity with HTTP status
     */
    @PreAuthorize(Constants.HAS_AUTHORITY_SCOPE_USER_WRITE)
    @PostMapping(Constants.WITHDRAW_ENDPOINT)
    public ResponseEntity<ResponseDto> withdraw(@RequestBody AccountDto accountDto) {
    	accountValidator.validateWithdrawRequest(accountDto);
        accountService.withdraw(accountDto.getFromAccountNumber(), accountDto.getAmount(), accountDto.getCardType());
        AuditService.logTransaction(Constants.WITHDRAW, 
            String.format("Withdrawn %s from account %s", accountDto.getAmount(), accountDto.getFromAccountNumber()));
               
        ResponseDto responseDto = new ResponseDto(
                "Withdraw Done",200,HttpStatus.OK);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
           
    }

    /**
     * Operation to transfer an amount from one account to another using a specified card type.
     * 
     * @param accountDto the model containing account information for transfer
     * @return a response entity with HTTP status
     */
    @PreAuthorize(Constants.HAS_AUTHORITY_SCOPE_USER_WRITE)
    @PostMapping(Constants.TRANSFER_ENDPOINT)
    public ResponseEntity<ResponseDto> transfer(@RequestBody AccountDto accountDto) {
    	accountValidator.validateTransferRequest(accountDto);
        accountService.transfer(accountDto.getFromAccountNumber(), accountDto.getToAccountNumber(), accountDto.getAmount(), accountDto.getCardType());
        AuditService.logTransaction(Constants.TRANSFER, 
            String.format("Transferred %s from %s to %s", accountDto.getAmount(), accountDto.getFromAccountNumber(), accountDto.getToAccountNumber()));
        ResponseDto responseDto = new ResponseDto(
                "Transfer Done",200,HttpStatus.OK);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}
