package com.neueda.test.atm.validation;

/**
 * 
 * @author Anubhav.Anand
 *
 */
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import com.neueda.test.atm.VO.AccountBalance;
import com.neueda.test.atm.controller.errorHandler.ValidationFailedException;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.utils.Message;

class SufficientAccountBalanceValidatorTest {
	
	private SufficientAccountBalanceValidator sufficientAccountBalanceValidator;
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() throws Exception {
		restTemplate = Mockito.mock(RestTemplate.class); 
		sufficientAccountBalanceValidator = new SufficientAccountBalanceValidator(restTemplate);
	}

	@Test
	void testIsValidWhenAccountBalanceSufficient() {
		final WithdrawalRequest request = new WithdrawalRequest(1000L, 100, 50);
		final AccountBalance accountBalance = new AccountBalance(100, 10, 110);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(accountBalance);
		assertTrue(sufficientAccountBalanceValidator.isValid(request));
	}
	
	@Test
	void testIsValidWhenAccountBalanceInsufficient() {
		final WithdrawalRequest request = new WithdrawalRequest(1000L, 100, 50000);
		final AccountBalance accountBalance = new AccountBalance(100, 10, 110);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(accountBalance);
		final ValidationFailedException exception = assertThrows(ValidationFailedException.class, () -> {
			sufficientAccountBalanceValidator.isValid(request);
		});
		assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
		assertEquals(exception.getMessage(), Message.INSUFFICIENT_ACCOUNT_BALANCE.message());
	}

}
