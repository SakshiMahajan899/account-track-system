package com.rabobank.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import com.rabobank.service.AuditService;


class AuditControllerTest {

	@InjectMocks
    private AuditController auditController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactionLogs_whenTransactionTypeIsTransfer_shouldReturnMockData() {
        String transactionType = "TRANSFER";
        List<String> logs = new ArrayList<>();
        logs.add("Transferred 100 from account 123 to account 456");
        
        try (MockedStatic<AuditService> mockedAuditService = mockStatic(AuditService.class)) {
            mockedAuditService.when(() -> AuditService.getTransactionLogs(transactionType)).thenReturn(logs);

            List<String> result = auditController.getTransactionLogs(transactionType);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("Transferred 100 from account 123 to account 456", result.get(0));
        }
    }

    @Test
    public void testGetTransactionLogs_whenTransactionTypeIsWithdraw_shouldReturnNoData() {
        String transactionType = "WITHDRAW";
        
        try (MockedStatic<AuditService> mockedAuditService = mockStatic(AuditService.class)) {
            mockedAuditService.when(() -> AuditService.getTransactionLogs(transactionType)).thenReturn(new ArrayList<>());

            List<String> result = auditController.getTransactionLogs(transactionType);

            assertNotNull(result);
            assertEquals(0, result.size());
        }
    }

    @Test
    public void testGetTransactionLogs__whenTransactionTypeIsTransfer_shouldReturnInvalidAuthorization() {
        String transactionType = "TRANSFER";
        
        try (MockedStatic<AuditService> mockedAuditService = mockStatic(AuditService.class)) {
            mockedAuditService.when(() -> AuditService.getTransactionLogs(transactionType)).thenThrow(new AccessDeniedException("Access Denied"));

            try {
                auditController.getTransactionLogs(transactionType);
            } catch (AccessDeniedException e) {
                assertEquals("Access Denied", e.getMessage());
            }
        }
    }
 

}
