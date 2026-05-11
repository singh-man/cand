package com.neueda.test.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.neueda.test.atm.VO.AccountBalance;
import com.neueda.test.atm.VO.DispensedCashDetails;
import com.neueda.test.atm.VO.TransactionDetails;
import com.neueda.test.atm.controller.errorHandler.ValidationFailedException;
import com.neueda.test.atm.dispenser.CurrencyDispenser;
import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.DispenserResult;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.service.entity.repository.ATMRepository;
import com.neueda.test.atm.utils.Message;
import com.neueda.test.atm.utils.UrlConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Service
@Slf4j
public class ATMService {

	private final ATMRepository atmRepository;

	private final RestTemplate restTemplate;

	private final CurrencyDispenser currencyDispenser;

	@Autowired
	public ATMService(final ATMRepository atmRepository, final RestTemplate restTemplate,
			final CurrencyDispenser currencyDispenser) {
		this.atmRepository = atmRepository;
		this.restTemplate = restTemplate;
		this.currencyDispenser = currencyDispenser;
	}

	public ATMCashDetails initializeAmountinATM(final ATMCashDetails atmCashDetails) {
		log.info("Initializing ATM cash box: {}", atmCashDetails);
		return atmRepository.save(atmCashDetails);
	}

	public TransactionDetails withdrawAmount(final WithdrawalRequest withdrawalRequest) {
		log.info("Withrawal request received: {}", withdrawalRequest);
		final ATMCashDetails atmCashDetails = atmRepository.getById(1L);
		final TransactionDetails transactionDetails = new TransactionDetails();
		final DispenserResult dispenserResult = currencyDispenser.dispense(atmCashDetails,
				initializeDispenserResult(withdrawalRequest.getAmount()));
		validateAmountLeftToBeDispensed(dispenserResult.getAmountLeftTobeDispensed());
		transactionDetails.setDispensedCashDetails(dispenserResult.getDispensedCashDetails());
		transactionDetails.setAccountBalance(debitAccountBalance(withdrawalRequest));
		updateATMRepo(atmCashDetails);
		return transactionDetails;
	}

	public AccountBalance getAccountBalance(long accountId, int pin) {
		final AccountBalance accountBalance = restTemplate.getForObject(UrlConstants.ACCOUNT_SERVICE.value()
				+ UrlConstants.CHECK_BALANCE.value() + accountId + UrlConstants.PIN.value() + pin,
				AccountBalance.class);
		log.info("Account balance fetched from account service: {}", accountBalance);
		return accountBalance;
	}

	private AccountBalance debitAccountBalance(final WithdrawalRequest withdrawalRequest) {
		final AccountBalance accountBalance = restTemplate.postForObject(
				UrlConstants.ACCOUNT_SERVICE.value() + UrlConstants.DEBIT.value(), withdrawalRequest,
				AccountBalance.class);
		log.info("Account debited. Current account balance: {}", accountBalance);
		return accountBalance;
	}

	private void updateATMRepo(final ATMCashDetails atmDetails) {
		atmRepository.save(atmDetails);
	}

	private void validateAmountLeftToBeDispensed(final Integer amountLeftToBeDispensed) {
		if (amountLeftToBeDispensed > 0) {
			log.error("Not all amount can be dispensed. Amount in excess: {}", amountLeftToBeDispensed);
			throw new ValidationFailedException(HttpStatus.BAD_REQUEST, Message.INSUFFICIENT_ATM_CASH.message());
		}
	}

	private DispenserResult initializeDispenserResult(final int amount) {
		return new DispenserResult(new DispensedCashDetails(), amount);
	}

}
