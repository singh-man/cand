package com.neueda.test.atm.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import com.neueda.test.atm.controller.errorHandler.ValidationFailedException;
import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.service.entity.repository.ATMRepository;
import com.neueda.test.atm.utils.Message;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public class SufficientATMCashValidatorTest {
	
	private ATMRepository atmRepository;
	
	private SufficientATMCashValidator sufficientATMCashValidator;
	
	@BeforeEach
	public void setup() {
		atmRepository = Mockito.mock(ATMRepository.class);
		sufficientATMCashValidator = new SufficientATMCashValidator(atmRepository);
	}

	@Test
	public void testIsValidWhenATMHasSufficientCash() {
		final ATMCashDetails atmCashDetails = new ATMCashDetails(10, 5, 10, 5);
		when(atmRepository.getById(ArgumentMatchers.any())).thenReturn(atmCashDetails);
		
		final WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setAmount(120);
		
		assertTrue(sufficientATMCashValidator.isValid(withdrawalRequest));
	}
	
	@Test
	public void testIsValidWhenATMHasInsufficientCash() {
		final ATMCashDetails atmCashDetails = new ATMCashDetails(1, 1, 1, 2);
		when(atmRepository.getById(ArgumentMatchers.any())).thenReturn(atmCashDetails);
		
		final WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setAmount(140);
		
		final ValidationFailedException exception = assertThrows(ValidationFailedException.class, () -> {
			sufficientATMCashValidator.isValid(withdrawalRequest);
		});
		assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
		assertEquals(exception.getMessage(), Message.INSUFFICIENT_ATM_CASH.message());
	}

}
