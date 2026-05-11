package com.neueda.test.atm.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.test.atm.VO.AccountBalance;
import com.neueda.test.atm.VO.TransactionDetails;
import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.service.ATMService;
import com.neueda.test.atm.validation.service.ValidationService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@RestController
@RequestMapping("/atm")
@Slf4j
public class ATMController {

	private final ATMService atmService;
	
	private final ValidationService<WithdrawalRequest> validatorService;

	@Autowired
	public ATMController(final ATMService atmService, final ValidationService<WithdrawalRequest> validatorService) {
		this.atmService = atmService;
		this.validatorService = validatorService;
	}

	@PostMapping
	public ResponseEntity<ATMCashDetails> initializeAmountinATM(@RequestBody final ATMCashDetails atmCashDetails) {
		log.info("Initializing currency in ATM cash box: {}", atmCashDetails);
		return new ResponseEntity<ATMCashDetails>(atmService.initializeAmountinATM(atmCashDetails), HttpStatus.CREATED);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<TransactionDetails> withdrawAmount(@RequestBody final WithdrawalRequest withdrawalRequest) {
		log.info("Withdrawal request received from user: {}", withdrawalRequest);
		validatorService.validate(withdrawalRequest);
		return new ResponseEntity<TransactionDetails>(atmService.withdrawAmount(withdrawalRequest), HttpStatus.OK);
	}
	
	@GetMapping("/checkBalance/accountId/{accountId}/pin/{pin}")
	public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable("accountId") final long accountId,
			@PathVariable("pin") final int pin) {
		log.info("Fetch account balance request received, accountId: {}", accountId);
		return new ResponseEntity<AccountBalance>(atmService.getAccountBalance(accountId, pin), HttpStatus.OK);
	}

}
