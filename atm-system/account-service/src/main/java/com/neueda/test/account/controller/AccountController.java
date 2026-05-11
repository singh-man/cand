package com.neueda.test.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.test.account.VO.AccountBalance;
import com.neueda.test.account.VO.AccountDebitInfo;
import com.neueda.test.account.entity.AccountDetails;
import com.neueda.test.account.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

	private final AccountService accountService;

	@Autowired
	public AccountController(final AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<AccountDetails> addAccountDetails(@RequestBody final AccountDetails accountDetails) {
		log.info("Adding new account: {}", accountDetails);
		return new ResponseEntity<AccountDetails>(accountService.addAccountDetails(accountDetails), HttpStatus.CREATED);
	}

	@GetMapping("/checkBalance/accountId/{accountId}/pin/{pin}")
	public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable("accountId") final Long accountId,
			@PathVariable("pin") final int pin) {
		log.info("Getting account balance for account: {}", accountId);
		return new ResponseEntity<AccountBalance>(accountService.getAccountBalance(accountId, pin), HttpStatus.OK);
	}

	@PostMapping("/debit")
	public ResponseEntity<AccountBalance> debitFromAccount(@RequestBody final AccountDebitInfo accountDebitInfo) {
		log.info("Requesting account debit: {}", accountDebitInfo);
		return new ResponseEntity<AccountBalance>(accountService.debitFromAccount(accountDebitInfo), HttpStatus.OK);
	}

}
