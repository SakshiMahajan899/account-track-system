/**
 * 
 */
package com.rabobank.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.service.AccountService;
import com.rabobank.service.AuditService;

/**
 * 
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountNumber) {
    	AuditService.logTransaction("getBalance " +" from account " + accountNumber);
       return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long accountNumber, @RequestParam BigDecimal amount, @RequestParam String cardType) {
        accountService.withdraw(accountNumber, amount, cardType);
        AuditService.logTransaction("Withdrawn " + amount + " from account " + accountNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{fromAccountNumber}/transfer")
    public ResponseEntity<Void> transfer(@PathVariable Long fromAccountNumber, @RequestParam Long toAccountNumber, @RequestParam BigDecimal amount, @RequestParam String cardType) {
        accountService.transfer(fromAccountNumber, toAccountNumber, amount, cardType);
        AuditService.logTransaction("Transferred " + amount + " from " + fromAccountNumber + " to " + toAccountNumber);
        return ResponseEntity.ok().build();
    }
}

