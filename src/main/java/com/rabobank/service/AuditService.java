/**
 *
 */
package com.rabobank.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class AuditService {
	private static Map<String, List<String>> transactionMap = new HashMap<>();

	public static void logTransaction(String transactionType, String logMessage) {
		// Check if the key already exists in the map
		if (transactionMap.containsKey(transactionType)) {
			// Add the transaction to the existing list
			transactionMap.get(transactionType).add(logMessage);
		} else {
			// Create a new list for the key and add the transaction
			List<String> transactions = new ArrayList<>();
			transactions.add(logMessage);
			transactionMap.put(transactionType, transactions);
		}
	}

	public static void logTransactionError(String transactionType, String logMessage) {
		// Check if the key already exists in the map
		if (transactionMap.containsKey(transactionType)) {
			// Add the transaction to the existing list
			transactionMap.get(transactionType).add(logMessage);
		} else {
			// Create a new list for the key and add the transaction
			List<String> transactions = new ArrayList<>();
			transactions.add(logMessage);
			transactionMap.put(transactionType, transactions);
		}
	}

	public static List<String> getTransactionLogs(String transactionType) {
		return transactionMap.getOrDefault(transactionType, Collections.emptyList());
	}

	public static Map<String, List<String>> getTransactionMap() {
		// TODO Auto-generated method stub
		return transactionMap;
	}
}
