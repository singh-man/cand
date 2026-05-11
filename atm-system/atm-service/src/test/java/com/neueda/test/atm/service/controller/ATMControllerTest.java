package com.neueda.test.atm.service.controller;

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
import com.neueda.test.atm.VO.AccountBalance;
import com.neueda.test.atm.VO.DispensedCashDetails;
import com.neueda.test.atm.VO.TransactionDetails;
import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.service.ATMService;
import com.neueda.test.atm.validation.service.ValidationService;

@WebMvcTest
public class ATMControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ATMService atmService;

	@MockBean
	private ValidationService<WithdrawalRequest> validationService;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testInitializeAmountinATM() throws Exception {
		final ATMCashDetails atmCashDetails = new ATMCashDetails(10, 20, 10, 10);
		Mockito.when(atmService.initializeAmountinATM(ArgumentMatchers.any())).thenReturn(atmCashDetails);
		final String json = mapper.writeValueAsString(atmCashDetails);

		mockMvc.perform(post("/atm").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.noOfFiveCurrency", Matchers.equalTo(10)))
				.andExpect(jsonPath("$.noOfTenCurrency", Matchers.equalTo(20)))
				.andExpect(jsonPath("$.noOfTwentyCurrency", Matchers.equalTo(10)))
				.andExpect(jsonPath("$.noOfFiftyCurrency", Matchers.equalTo(10)));
	}

	@Test
	public void testGetAccountBalance() throws Exception {
		final AccountBalance accountBalance = new AccountBalance(100.0, 10.0, 110.0);
		Mockito.when(atmService.getAccountBalance(987654321L, 4321)).thenReturn(accountBalance);

		mockMvc.perform(get("/atm/checkBalance/accountId/987654321/pin/4321")).andExpect(status().isOk())
				.andExpect(jsonPath("$.regularBalance", Matchers.equalTo(100.0)))
				.andExpect(jsonPath("$.overDraftBalance", Matchers.equalTo(10.0)))
				.andExpect(jsonPath("$.maxWithdrawalAmount", Matchers.equalTo(110.0)));
	}

	@Test
	public void testDebitFromAccount() throws Exception {
		final AccountBalance accountBalance = new AccountBalance(100.0, 10.0, 110.0);
		final TransactionDetails transactionDetails = new TransactionDetails(new DispensedCashDetails(1, 1, 0, 1), accountBalance);
		Mockito.when(validationService.validate(ArgumentMatchers.any())).thenReturn(true);
		Mockito.when(atmService.withdrawAmount(ArgumentMatchers.any())).thenReturn(transactionDetails);
		final String json = mapper.writeValueAsString(transactionDetails);

		mockMvc.perform(post("/atm/withdraw").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.dispensedCashDetails.noOfFiveCurrency", Matchers.equalTo(1)))
				.andExpect(jsonPath("$.dispensedCashDetails.noOfTenCurrency", Matchers.equalTo(1)))
				.andExpect(jsonPath("$.dispensedCashDetails.noOfTwentyCurrency", Matchers.equalTo(0)))
				.andExpect(jsonPath("$.dispensedCashDetails.noOfFiftyCurrency", Matchers.equalTo(1)))
				.andExpect(jsonPath("$.accountBalance.regularBalance", Matchers.equalTo(100.0)))
				.andExpect(jsonPath("$.accountBalance.overDraftBalance", Matchers.equalTo(10.0)))
				.andExpect(jsonPath("$.accountBalance.maxWithdrawalAmount", Matchers.equalTo(110.0)));
	}

}
