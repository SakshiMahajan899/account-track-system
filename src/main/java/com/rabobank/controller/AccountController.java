/**
 * 
 */
package com.rabobank.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.model.Account;
import com.rabobank.model.AccountModel;
import com.rabobank.service.AccountService;
import com.rabobank.service.AuditService;
import com.rabobank.utils.BankUtil;

/**
 * 
 */
@RestController
@RequestMapping("/api/accounts")
@EnableMethodSecurity
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    @PostMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestBody AccountModel account) {
        String maskedAccountNumber = BankUtil.maskNumber(account.getFromAccountNumber());
        AuditService.logTransaction("getBalance from account " + maskedAccountNumber);
        return ResponseEntity.ok(accountService.getBalance(account.getFromAccountNumber()));
    }
    

    @PreAuthorize("hasAuthority('SCOPE_user.write')")
    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody AccountModel account) {
        accountService.withdraw(account.getFromAccountNumber(), account.getAmount(), account.getCardType());
        AuditService.logTransaction("Withdrawn " + account.getAmount() + " from account " + account.getFromAccountNumber());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_user.write')")
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody AccountModel account) {
        accountService.transfer(account.getFromAccountNumber(),account.getToAccountNumber(), account.getAmount(), account.getCardType());
        AuditService.logTransaction("Transferred " +  account.getAmount() + " from " + account.getFromAccountNumber() + " to " + account.getToAccountNumber());
        return ResponseEntity.ok().build();
    }
}

