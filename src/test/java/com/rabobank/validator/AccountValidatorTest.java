package com.rabobank.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rabobank.dto.AccountDto;
import com.rabobank.exception.FunctionalException;
import com.rabobank.util.CardType;

class AccountValidatorTest {

	private AccountValidator accountValidator;

    @BeforeEach
    public void setup_shouldThrowFunctionalExp() {
        accountValidator = new AccountValidator();
    }

    @Test
    public void testValidateTransferRequest_whenDataIsValid_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType(CardType.CREDIT.name());
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setFromAccountNumber("123456789");
        accountDto.setToAccountNumber("987654321");

        accountValidator.validateTransferRequest(accountDto);
    }

    @Test
    public void testValidateTransferRequest_whenInvalidCardType_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType("INVALID");
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setFromAccountNumber("123456789");
        accountDto.setToAccountNumber("987654321");

        assertThrows(FunctionalException.class, () -> accountValidator.validateTransferRequest(accountDto));
    }

    @Test
    public void testValidateTransferRequest_whenInvalidAmount_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType(CardType.CREDIT.name());
        accountDto.setAmount(new BigDecimal("-100.00")); // Invalid amount
        accountDto.setFromAccountNumber("123456789");
        accountDto.setToAccountNumber("987654321");

        assertThrows(FunctionalException.class, () -> accountValidator.validateTransferRequest(accountDto));
    }

    @Test
    public void testValidateTransferRequest_whenInvalidAccountNumber_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType(CardType.CREDIT.name());
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setFromAccountNumber(""); // Invalid from account number
        accountDto.setToAccountNumber("987654321");

        assertThrows(FunctionalException.class, () -> accountValidator.validateTransferRequest(accountDto));
    }

    @Test
    public void testValidateWithdrawRequest_whenDataIsValid_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType(CardType.DEBIT.name());
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setFromAccountNumber("123456789");

        accountValidator.validateWithdrawRequest(accountDto);
    }

    @Test
    public void testValidateWithdrawRequest_whenInvalidCardType_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType("INVALID");
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setFromAccountNumber("123456789");

        assertThrows(FunctionalException.class, () -> accountValidator.validateWithdrawRequest(accountDto));
    }

    @Test
    public void testValidateWithdrawRequest_whenInvalidAmount_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType(CardType.DEBIT.name());
        accountDto.setAmount(new BigDecimal("-100.00")); // Invalid amount
        accountDto.setFromAccountNumber("123456789");

        assertThrows(FunctionalException.class, () -> accountValidator.validateWithdrawRequest(accountDto));
    }

    @Test
    public void testValidateWithdrawRequest_whenInvalidAccountNumber_shouldThrowFunctionalExp() {
        AccountDto accountDto = new AccountDto();
        accountDto.setCardType(CardType.DEBIT.name());
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setFromAccountNumber(""); // Invalid from account number

        assertThrows(FunctionalException.class, () -> accountValidator.validateWithdrawRequest(accountDto));
    }

    @Test
    public void testValidateCardType_whenInvalidCardType_shouldThrowFunctionalExp() {
        assertThrows(FunctionalException.class, () -> accountValidator.validateCardType("INVALID"));
    }

    @Test
    public void testValidateAmount_WhenNullAmount_shouldThrowFunctionalExp() {
        assertThrows(FunctionalException.class, () -> accountValidator.validateAmount(null));
    }

    @Test
    public void testValidateAmount_whenNegativeAmount_shouldThrowFunctionalExp() {
        assertThrows(FunctionalException.class, () -> accountValidator.validateAmount(new BigDecimal("-100.00")));
    }

    @Test
    public void testValidateAccountNumber_whenEmptyAccountNumber_shouldThrowFunctionalExp_shouldThrowFunctionalExp() {
        assertThrows(FunctionalException.class, () -> accountValidator.validateAccountNumber(""));
    }

}
