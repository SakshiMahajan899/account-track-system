package com.rabobank.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabobank.dto.AccountDto;
import com.rabobank.dto.CustomerDto;
import com.rabobank.dto.ResponseDto;
import com.rabobank.repository.AccountRepository;
import com.rabobank.service.AccountService;
import com.rabobank.util.Constants;
import com.rabobank.validator.AccountValidator;
import com.rabobank.validator.CustomerValidator;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIT {

	

	    @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @MockBean
	    private AccountService accountService;

	    @MockBean
	    private AccountValidator accountValidator;

	    @MockBean
	    private CustomerValidator customerValidator;

	    @Mock
	    private AccountRepository accountRepository;
	    @BeforeEach
	    public void setup() {
	        Mockito.reset(accountService, accountValidator, customerValidator);
	    }
	    
	    @Test
	    @WithMockUser(authorities = "SCOPE_user.read")
	    public void shouldReturnBalance_whenGetBalance() throws Exception {
	        CustomerDto customerDto = new CustomerDto();
	        customerDto.setCustomerId(1L);

	        Map<String, BigDecimal> balanceMap = new HashMap<>();
	        balanceMap.put("123456789", new BigDecimal("1000.00"));

	        Mockito.when(accountService.getBalance(anyLong())).thenReturn(balanceMap);

	        MvcResult result = mockMvc.perform(post(Constants.API_ACCOUNTS + Constants.BALANCE)
	                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
	                .header("Authorization", "Bearer vvaggs")
	                .content(objectMapper.writeValueAsString(customerDto)))
	                .andExpect(status().isOk())
	                .andReturn();

	        String jsonResponse = result.getResponse().getContentAsString();
	        Map<String, BigDecimal> response = objectMapper.readValue(jsonResponse, Map.class);

	        assertEquals(1, response.size());
	        assertEquals(new BigDecimal("1000.00"), response.get("123456789"));
	    }
	    @Test
	    @WithMockUser(authorities = "SCOPE_user.write")
	    public void shouldReturnForbidden_whenGetBalance_withInvalidAuthority() throws Exception {
	        CustomerDto customerDto = new CustomerDto();
	        customerDto.setCustomerId(1L);

	        mockMvc.perform(post(Constants.API_ACCOUNTS + Constants.BALANCE)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(customerDto)))
	                .andExpect(status().isForbidden());
	    }

	    @Test
	    @WithMockUser(authorities = "SCOPE_user.write")
	    public void shouldReturnSuccess_whenWithdraw() throws Exception {
	        AccountDto accountDto = new AccountDto();
	        accountDto.setFromAccountNumber("123456789");
	        accountDto.setAmount(new BigDecimal("100.00"));
	        accountDto.setCardType("CREDIT");

	        Mockito.doNothing().when(accountService).withdraw(any(String.class), any(BigDecimal.class), any(String.class));

	        MvcResult result = mockMvc.perform(post(Constants.API_ACCOUNTS + Constants.WITHDRAW_ENDPOINT)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(accountDto)))
	                .andExpect(status().isOk())
	                .andReturn();

	        String jsonResponse = result.getResponse().getContentAsString();
	        ResponseDto response = objectMapper.readValue(jsonResponse, ResponseDto.class);

	        assertEquals("Withdraw Done", response.getMessage());
	        assertEquals(200, response.getStatus());
	    }

	    @Test
	    @WithMockUser(authorities = "SCOPE_user.write")
	    public void shouldReturnSuccess_whenTransfer() throws Exception {
	        AccountDto accountDto = new AccountDto();
	        accountDto.setFromAccountNumber("123456789");
	        accountDto.setToAccountNumber("987654321");
	        accountDto.setAmount(new BigDecimal("200.00"));
	        accountDto.setCardType("DEBIT");

	        Mockito.doNothing().when(accountService).transfer(any(String.class), any(String.class), any(BigDecimal.class), any(String.class));

	        MvcResult result = mockMvc.perform(post(Constants.API_ACCOUNTS + Constants.TRANSFER_ENDPOINT)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(accountDto)))
	                .andExpect(status().isOk())
	                .andReturn();

	        String jsonResponse = result.getResponse().getContentAsString();
	        ResponseDto response = objectMapper.readValue(jsonResponse, ResponseDto.class);

	        assertEquals("Transfer Done", response.getMessage());
	        assertEquals(200, response.getStatus());
	    }
}
