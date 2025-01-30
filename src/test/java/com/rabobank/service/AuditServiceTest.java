package com.rabobank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditServiceTest {

	
	@BeforeEach
    public void setup() {
        AuditService.getTransactionMap().clear();
    }

    @Test
    public void testLogTransaction() {
        AuditService.logTransaction("Type1", "Log1");
        AuditService.logTransaction("Type1", "Log2");
        AuditService.logTransaction("Type2", "Log2");
        List<String> logs = AuditService.getTransactionLogs("Type1");
        assertEquals(2, logs.size());
        assertTrue(logs.contains("Log1"));
    }

    @Test
    public void testLogTransactionError() {
        AuditService.logTransaction("Type2", "ErrorLog1");
        List<String> logs = AuditService.getTransactionLogs("Type2");
        assertEquals(1, logs.size());
        assertTrue(logs.contains("ErrorLog1"));
    }

    @Test
    public void testGetTransactionLogsForNonExistingType() {
        List<String> logs = AuditService.getTransactionLogs("NonExistingType");
        assertTrue(logs.isEmpty());
    }
}
