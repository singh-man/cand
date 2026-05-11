package com.neueda.test.atm.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import com.neueda.test.atm.VO.AccountBalance;
import com.neueda.test.atm.controller.errorHandler.ValidationFailedException;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.utils.Message;
import com.neueda.test.atm.utils.UrlConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Slf4j
public class SufficientAccountBalanceValidator implements Validator<WithdrawalRequest> {

	private final RestTemplate restTemplate;

	public SufficientAccountBalanceValidator(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public boolean isValid(final WithdrawalRequest withdrawalRequest) {
		final AccountBalance accountBalance = restTemplate
				.getForObject(
						UrlConstants.ACCOUNT_SERVICE.value() + UrlConstants.CHECK_BALANCE.value()
								+ withdrawalRequest.getAccountId() + UrlConstants.PIN.value() + withdrawalRequest.getPin(),
						AccountBalance.class);
		if (accountBalance.getMaxWithdrawalAmount() < withdrawalRequest.getAmount()) {
			log.info("Validation failed: {}, WithdrawalRequest: {}, AccountBalance: {}",
					SufficientAccountBalanceValidator.class.getSimpleName(), withdrawalRequest, accountBalance);
			throw new ValidationFailedException(HttpStatus.BAD_REQUEST, Message.INSUFFICIENT_ACCOUNT_BALANCE.message());
		}
		return true;
	}

}
