package com.rabobank.validator;

import org.springframework.stereotype.Component;

import com.rabobank.exception.FunctionalError;
import com.rabobank.exception.FunctionalException;

/**
 * Component for validating account information.
 */
@Component
public class CustomerValidator {

	
	 /**
     * Validates that the customer Id is valid (non-null and positive).
     * 
     * @param customerId the customer Id to validate
	 * @throws FunctionalException if the customer Id is null or less than or equal to zero
     */
    public void validateCustomerId(Long customerId) {
        if (customerId == null || customerId <= 0) {
            throw new FunctionalException(FunctionalError.INVALID_CUSTOMER_ID.getErrorCode(),FunctionalError.INVALID_CUSTOMER_ID.getErrorMessage());
        }
    }
	
}
