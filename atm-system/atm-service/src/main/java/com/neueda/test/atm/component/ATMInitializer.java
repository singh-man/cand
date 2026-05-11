package com.neueda.test.atm.component;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.service.entity.repository.ATMRepository;
import com.neueda.test.atm.validation.InvalidWithdrwalAmountValidator;
import com.neueda.test.atm.validation.SufficientATMCashValidator;
import com.neueda.test.atm.validation.SufficientAccountBalanceValidator;
import com.neueda.test.atm.validation.service.ValidationRegistrationService;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Component
public class ATMInitializer {

	private final RestTemplate restTemplate;

	private final ValidationRegistrationService<WithdrawalRequest> validationRegistrationService;

	private final ATMRepository atmRepository;

	@Autowired
	public ATMInitializer(final RestTemplate restTemplate,
			final ValidationRegistrationService<WithdrawalRequest> validationRegistrationService, final ATMRepository atmRepository) {
		this.restTemplate = restTemplate;
		this.validationRegistrationService = validationRegistrationService;
		this.atmRepository = atmRepository;
	}

	@PostConstruct
	private void init() {
		validationRegistrationService.register(new InvalidWithdrwalAmountValidator());
		validationRegistrationService.register(new SufficientATMCashValidator(atmRepository));
		validationRegistrationService.register(new SufficientAccountBalanceValidator(restTemplate));
	}

}
