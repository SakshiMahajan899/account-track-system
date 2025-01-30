package com.rabobank.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rabobank.dto.AccountDto;
import com.rabobank.dto.CustomerDto;
import com.rabobank.dto.ResponseDto;
import com.rabobank.exception.FunctionalError;
import com.rabobank.exception.FunctionalException;
import com.rabobank.service.AccountService;
import com.rabobank.validator.AccountValidator;
import com.rabobank.validator.CustomerValidator;


class AccountControllerTest {

	@InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountValidator accountValidator;

    @Mock
    private CustomerValidator customerValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBalance_shouldReturnResultOk_WhenRequestIsValid() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);

        Map<String, BigDecimal> balanceMap = new HashMap<>();
        balanceMap.put("123", new BigDecimal("1000.00"));
        
        when(accountService.getBalance(customerDto.getCustomerId())).thenReturn(balanceMap);

        Map<String, BigDecimal> result = accountController.getBalance(customerDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1000.00"), result.get("123"));
        verify(customerValidator).validateCustomerId(customerDto.getCustomerId());
    }
    
    @Test
    public void testWithdraw_shouldReturnResultOk_WhenRequestIsValid() {
        AccountDto accountDto = new AccountDto();
        accountDto.setFromAccountNumber("123");
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setCardType("CREDIT");

        ResponseEntity<ResponseDto> response = accountController.withdraw(accountDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Withdraw Done", response.getBody().getMessage());
        verify(accountValidator).validateWithdrawRequest(accountDto);
        verify(accountService).withdraw(accountDto.getFromAccountNumber(), accountDto.getAmount(), accountDto.getCardType());
    }
    
    @Test
    public void testTransfer_shouldReturnResultOk_WhenRequestIsValid() {
        AccountDto accountDto = new AccountDto();
        accountDto.setFromAccountNumber("123");
        accountDto.setToAccountNumber("456");
        accountDto.setAmount(new BigDecimal("200.00"));
        accountDto.setCardType("DEBIT");

        ResponseEntity<ResponseDto> response = accountController.transfer(accountDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transfer Done", response.getBody().getMessage());
        verify(accountValidator).validateTransferRequest(accountDto);
        verify(accountService).transfer(accountDto.getFromAccountNumber(), accountDto.getToAccountNumber(), accountDto.getAmount(), accountDto.getCardType());
    }
    @Test
    public void testGetBalance_shouldThrowException_WhenInvalidCustomerId() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(-1L); // Invalid customer ID

        doThrow(new FunctionalException(FunctionalError.INVALID_CUSTOMER_ID.getCode(),"Invalid customer ID")).when(customerValidator).validateCustomerId(customerDto.getCustomerId());

        try {
            accountController.getBalance(customerDto);
        } catch (FunctionalException e) {
            assertEquals("Invalid customer ID", e.getMessage());
        }
    }
    
    @Test
    public void testWithdraw_shouldThrowException_WhenInvalidCardType() {
        AccountDto accountDto = new AccountDto();
        accountDto.setFromAccountNumber("123");
        accountDto.setAmount(new BigDecimal("100.00"));
        accountDto.setCardType("INVALID"); // Invalid card type

        doThrow(new FunctionalException(FunctionalError.INVALID_CARD_TYPE.getCode(),"Invalid card type")).when(accountValidator).validateWithdrawRequest(accountDto);

        try {
            accountController.withdraw(accountDto);
        } catch (FunctionalException e) {
            assertEquals("Invalid card type", e.getMessage());
        }
    }
    
    @Test
    public void testTransfer_shouldThrowException_WhenInvalidRequest() {
        AccountDto accountDto = new AccountDto();
        accountDto.setFromAccountNumber("123");
        accountDto.setToAccountNumber("456");
        accountDto.setAmount(new BigDecimal("200.00"));
        accountDto.setCardType("INVALID"); // Invalid card type

        doThrow(new FunctionalException(FunctionalError.INVALID_CARD_TYPE.getCode(),"Invalid card type")).when(accountValidator).validateTransferRequest(accountDto);

        try {
            accountController.transfer(accountDto);
        } catch (FunctionalException e) {
            assertEquals("Invalid card type", e.getMessage());
        }
    }
    

}
