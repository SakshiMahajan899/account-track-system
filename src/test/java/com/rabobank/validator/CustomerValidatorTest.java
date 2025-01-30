package com.rabobank.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rabobank.exception.FunctionalException;

class CustomerValidatorTest {

	 private CustomerValidator customerValidator;

	    @BeforeEach
	    public void setup() {
	        customerValidator = new CustomerValidator();
	    }

	    @Test
	    public void shouldNotThrowException_whenCustomerIdIsValid() {
	        customerValidator.validateCustomerId(1L); // Valid customer ID
	    }

	    @Test
	    public void shouldThrowFunctionalException_whenCustomerIdIsNull() {
	        assertThrows(FunctionalException.class, () -> customerValidator.validateCustomerId(null));
	    }

	    @Test
	    public void shouldThrowFunctionalException_whenCustomerIdIsNegative() {
	        assertThrows(FunctionalException.class, () -> customerValidator.validateCustomerId(-1L));
	    }

	    @Test
	    public void shouldThrowFunctionalException_whenCustomerIdIsZero() {
	        assertThrows(FunctionalException.class, () -> customerValidator.validateCustomerId(0L));
	    }
}
