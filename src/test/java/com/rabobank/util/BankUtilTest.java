package com.rabobank.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class BankUtilTest {
	
	 @Test
	    void testMaskNumber_withValidNumber() {
	        // Test case: Valid number with multiple digits
	        Long accountNumber = 1234L;
	        String result = BankUtil.maskNumber(accountNumber);
	        assertEquals("****", result, "The number should be masked correctly");
	    }

	    @Test
	    void testMaskNumber_withSingleDigitNumber() {
	        // Test case: Single digit number (no digits to mask)
	        Long accountNumber = 5L;
	        String result = BankUtil.maskNumber(accountNumber);
	        assertEquals("*", result, "The number should remain the same for a single digit");
	    }

	    @Test
	    void testMaskNumber_withEmptyNumber() {
	        // Test case: Null or empty input (Although the method doesn't handle null, it handles non-positive numbers gracefully)
	        Long accountNumber = 0L;
	        String result = BankUtil.maskNumber(accountNumber);
	        assertEquals("*", result, "The number should remain the same for a zero value");
	    }

	    @Test
	    void testMaskNumber_withLargeNumber() {
	        // Test case: Large account number
	        Long accountNumber = 12345L;
	        String result = BankUtil.maskNumber(accountNumber);
	        assertEquals("*****", result, "The number should be masked correctly even for large numbers");
	    }


}
