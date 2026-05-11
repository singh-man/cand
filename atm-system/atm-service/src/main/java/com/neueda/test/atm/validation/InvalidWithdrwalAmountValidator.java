package com.neueda.test.atm.validation;

import org.springframework.http.HttpStatus;

import com.neueda.test.atm.controller.errorHandler.ValidationFailedException;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.utils.CurrencyValue;
import com.neueda.test.atm.utils.Message;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Slf4j
public class InvalidWithdrwalAmountValidator implements Validator<WithdrawalRequest> {

	@Override
	public boolean isValid(final WithdrawalRequest withdrawalRequest) {
		if (withdrawalRequest.getAmount() % CurrencyValue.getMinimumDenomination().value() != 0) {
			log.info("Validation failed: {}, WithdrawalRequest: {}",
					InvalidWithdrwalAmountValidator.class.getSimpleName(), withdrawalRequest);
			throw new ValidationFailedException(HttpStatus.BAD_REQUEST,
					Message.INVALID_AMOUNT.message() + CurrencyValue.getMinimumDenomination().value());
		}

		return true;
	}
}
