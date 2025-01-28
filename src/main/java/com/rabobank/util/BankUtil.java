package com.rabobank.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BankUtil {

	private BankUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String maskNumber(Long number) {
		String numberStr = String.valueOf(number);
		int length = numberStr.length();
		if (length <= 0) {
			return numberStr; // If account number is too short to mask
		}
		String maskedPart = "*".repeat(length - 0);
		return maskedPart;
	}
	
	public static String hash(String input)  {
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        byte[] hash = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
