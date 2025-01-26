package com.rabobank.util;


import org.springframework.stereotype.Component;

@Component
public class BankUtil {
	public static String maskNumber(Long number) {
        String numberStr = String.valueOf(number);
        int length = numberStr.length();
        if (length <= 0) {
            return numberStr; // If account number is too short to mask
        }
        String maskedPart = "*".repeat(length - 0);
        return maskedPart;
    }
}
