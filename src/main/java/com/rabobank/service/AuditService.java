/**
 * 
 */
package com.rabobank.service;

/**
 * 
 */
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private static List<String> transactionLogs = new ArrayList<>();

    public static void logTransaction(String logMessage) {
        transactionLogs.add(logMessage);
    }

    public static List<String> getTransactionLogs() {
        return transactionLogs;
    }
}

