package com.rabobank.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rabobank.exception.FunctionalException;

@ExtendWith(MockitoExtension.class)
public class CardFeeCalculatorTest {

    @InjectMocks
    private CardFeeCalculator cardFeeCalculator;

    @BeforeEach
    void setup() {
        cardFeeCalculator = new CardFeeCalculator();
    }

    @Test
    void testCalculateFee_shouldReturnFeeOnePerc_WhenCreditCard() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal expectedFee = new BigDecimal("1.00"); // Assume CreditCard applies 1.0% charge
        BigDecimal actualFee = cardFeeCalculator.calculateFee("CREDIT", amount);
        assertEquals(expectedFee, actualFee);
    }

    @Test
    void testCalculateFee_shouldReturnFeeZero_whenDebitCard() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal expectedFee = new BigDecimal("0"); // Assume DebitCard applies 0% charge
        BigDecimal actualFee = cardFeeCalculator.calculateFee("DEBIT", amount);
        assertEquals(expectedFee, actualFee);
    }

    @Test
    void testCalculateFee_shouldReturnFunctionalError_whenInvalidCardType() {
        BigDecimal amount = new BigDecimal("100");
        assertThrows(FunctionalException.class, () -> {
            cardFeeCalculator.calculateFee("UNKNOWN", amount);
        });
    }
}