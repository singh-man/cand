package com.neueda.test.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.neueda.test.account.VO.AccountBalance;
import com.neueda.test.account.VO.AccountDebitInfo;
import com.neueda.test.account.controller.exceptionHandler.ValidationFailedException;
import com.neueda.test.account.entity.AccountDetails;
import com.neueda.test.account.repository.AccountRepository;
import com.neueda.test.account.util.Message;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Service
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;

	@Autowired
	public AccountService(final AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public AccountDetails addAccountDetails(final AccountDetails accountDetails) {
		return accountRepository.save(accountDetails);
	}

	public AccountBalance getAccountBalance(final Long accountId, final int pin) {
		final AccountDetails accountDetails = accountRepository.getById(accountId);
		validatePin(pin, accountDetails.getPin());
		return getAccountBalance(accountDetails);
	}

	public AccountBalance debitFromAccount(final AccountDebitInfo accountDebitInfo) {
		final AccountDetails accountDetails = accountRepository.getById(accountDebitInfo.getAccountId());
		log.info("Account details fecthed from db: {}", accountDetails);
		validatePin(accountDebitInfo.getPin(), accountDetails.getPin());
		if (accountDetails.getOpeningBalance() >= accountDebitInfo.getAmount()) {
			accountDetails.setOpeningBalance(accountDetails.getOpeningBalance() - accountDebitInfo.getAmount());
		} else {
			accountDetails.setOverDraft(accountDetails.getOverDraft()
					- (accountDebitInfo.getAmount() - accountDetails.getOpeningBalance()));
			accountDetails.setOpeningBalance(0);
		}
		accountRepository.save(accountDetails);
		return getAccountBalance(accountDetails);
	}
	
	private void validatePin(final int receivedPin, final int actualPin) {
		if (receivedPin != actualPin) {
			log.error("Incorrect pin supplied: {}", receivedPin);
			throw new ValidationFailedException(HttpStatus.UNAUTHORIZED, Message.INCORRECT_PIN.message());
		}
	}

	private AccountBalance getAccountBalance(final AccountDetails accountDetails) {
		final double maxWithdrwalAmount = accountDetails.getOpeningBalance() + accountDetails.getOverDraft();
		return new AccountBalance(accountDetails.getOpeningBalance(), accountDetails.getOverDraft(),
				maxWithdrwalAmount > 0 ? maxWithdrwalAmount : 0.0);
	}

}
