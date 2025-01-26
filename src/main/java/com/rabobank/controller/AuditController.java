/**
 * 
 */
package com.rabobank.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.service.AuditService;


@RestController
@RequestMapping("/api/accounts/audit")
@EnableMethodSecurity
public class AuditController {
	 // Endpoint to get transaction logs
    @GetMapping("/logs")
    @PreAuthorize("hasAuthority('SCOPE_admin.audit')")
    public List<String> getTransactionLogs() {
        return AuditService.getTransactionLogs();
    }
}
