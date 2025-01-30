package com.rabobank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DebitCardTest {

    private DebitCard debitCard;

    @BeforeEach
    public void setup() {
        debitCard = new DebitCard();
    }

    @Test
    public void testApplyCharges_shouldReturnZero_whenDebitCard() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal expectedFee = BigDecimal.ZERO; // No extra charges for debit card
        BigDecimal actualFee = debitCard.applyCharges(amount);
        assertEquals(expectedFee, actualFee);
    }

}
