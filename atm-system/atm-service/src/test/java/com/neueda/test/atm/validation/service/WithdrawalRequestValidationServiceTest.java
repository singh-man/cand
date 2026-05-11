package com.neueda.test.atm.validation.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.validation.Validator;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public class WithdrawalRequestValidationServiceTest {
	
	private WithdrawalRequestValidationService withdrawalRequestValidationService;
	
	private Validator<WithdrawalRequest> validator;
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		withdrawalRequestValidationService = new WithdrawalRequestValidationService();
		validator = Mockito.mock(Validator.class); 
	}

	@Test
	void testWhenValidationIsTrue() {
		withdrawalRequestValidationService.register(validator);
		Mockito.when(validator.isValid(Mockito.any())).thenReturn(true);
		assertTrue(withdrawalRequestValidationService.validate(new WithdrawalRequest()));
	}
	
	@Test
	void testWhenValidationIsFalse() {
		withdrawalRequestValidationService.register(validator);
		Mockito.when(validator.isValid(Mockito.any())).thenReturn(false);
		assertFalse(withdrawalRequestValidationService.validate(new WithdrawalRequest()));
	}

}
