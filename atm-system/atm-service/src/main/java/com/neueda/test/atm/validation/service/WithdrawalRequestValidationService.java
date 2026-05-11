package com.neueda.test.atm.validation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.validation.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Service
@Slf4j
public class WithdrawalRequestValidationService implements ValidationService<WithdrawalRequest>, ValidationRegistrationService<WithdrawalRequest> {
	
	private List<Validator<WithdrawalRequest>> withdrawalRequestValidations = new ArrayList<>();
	
	@Override
	public void register(final Validator<WithdrawalRequest> validator) {
		withdrawalRequestValidations.add(validator);
	}

	@Override
	public boolean validate(final WithdrawalRequest withdrawalRequest) {
		log.debug("Validating WithdrawalRequest: {}", withdrawalRequest);
		return withdrawalRequestValidations.stream().allMatch(Validator -> Validator.isValid(withdrawalRequest));
	}

}
