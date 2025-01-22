/**
 * 
 */
package com.rabobank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.service.AuditService;

/**
 * 
 */
@RestController
@RequestMapping("/api/accounts/audit")
public class AuditController {
	 // Endpoint to get transaction logs
    @GetMapping("/logs")
    public List<String> getTransactionLogs() {
        return AuditService.getTransactionLogs();
    }
}
