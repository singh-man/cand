package com.neueda.test.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.test.account.VO.AccountBalance;
import com.neueda.test.account.entity.AccountDetails;
import com.neueda.test.account.service.AccountService;

@WebMvcTest
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testAddAccountDetails() throws Exception {
		final AccountDetails accountDetails = new AccountDetails(987654321, 4321, 1230.0, 150.0);
		Mockito.when(accountService.addAccountDetails(ArgumentMatchers.any())).thenReturn(accountDetails);
		final String json = mapper.writeValueAsString(accountDetails);

		mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.accountId", Matchers.equalTo(987654321)))
				.andExpect(jsonPath("$.pin", Matchers.equalTo(4321)))
				.andExpect(jsonPath("$.openingBalance", Matchers.equalTo(1230.0)))
				.andExpect(jsonPath("$.overDraft", Matchers.equalTo(150.0)));
	}

	@Test
	public void testGetAccountBalance() throws Exception {
		final AccountBalance accountBalance = new AccountBalance(100.0, 10.0, 110.0);
		Mockito.when(accountService.getAccountBalance(987654321L, 4321)).thenReturn(accountBalance);

		mockMvc.perform(get("/accounts/checkBalance/accountId/987654321/pin/4321")).andExpect(status().isOk())
				.andExpect(jsonPath("$.regularBalance", Matchers.equalTo(100.0)))
				.andExpect(jsonPath("$.overDraftBalance", Matchers.equalTo(10.0)))
				.andExpect(jsonPath("$.maxWithdrawalAmount", Matchers.equalTo(110.0)));
	}

	@Test
	public void testDebitFromAccount() throws Exception {
		final AccountBalance accountBalance = new AccountBalance(100.0, 10.0, 110.0);
		Mockito.when(accountService.debitFromAccount(ArgumentMatchers.any())).thenReturn(accountBalance);
		final String json = mapper.writeValueAsString(accountBalance);

		mockMvc.perform(post("/accounts/debit").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.regularBalance", Matchers.equalTo(100.0)))
				.andExpect(jsonPath("$.overDraftBalance", Matchers.equalTo(10.0)))
				.andExpect(jsonPath("$.maxWithdrawalAmount", Matchers.equalTo(110.0)));
	}

}
