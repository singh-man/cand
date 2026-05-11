package com.neueda.test.atm.validation;

import org.springframework.http.HttpStatus;

import com.neueda.test.atm.controller.errorHandler.ValidationFailedException;
import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.service.entity.repository.ATMRepository;
import com.neueda.test.atm.utils.AmountBuilder;
import com.neueda.test.atm.utils.Message;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Slf4j
public class SufficientATMCashValidator implements Validator<WithdrawalRequest> {

	private final ATMRepository atmRepository;

	public SufficientATMCashValidator(final ATMRepository atmRepository) {
		this.atmRepository = atmRepository;
	}

	@Override
	public boolean isValid(final WithdrawalRequest withdrawalRequest) {
		final ATMCashDetails atmDetails = atmRepository.getById(1L);
		if (withdrawalRequest.getAmount() > getAvailableAmountInATM(atmDetails)) {
			log.info("Validation failed: {}, WithdrawalRequest: {}",
					SufficientATMCashValidator.class.getSimpleName(), withdrawalRequest);
			throw new ValidationFailedException(HttpStatus.BAD_REQUEST, Message.INSUFFICIENT_ATM_CASH.message());
		}
		return true;
	}

	private Integer getAvailableAmountInATM(final ATMCashDetails atmDetails) {
		return new AmountBuilder().setNoOfFiftyCurrency(atmDetails.getNoOfFiftyCurrency())
				.setNoOfTwentyCurrency(atmDetails.getNoOfTwentyCurrency())
				.setNoOfTenCurrency(atmDetails.getNoOfTenCurrency())
				.setNoOfFiveCurrency(atmDetails.getNoOfFiveCurrency()).getTotalAmount();
	}

}
