package com.zinkworks.atmmachine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zinkworks.atmmachine.controller.dto.AccountBalanceDTO;
import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.entity.UserAccount;
import com.zinkworks.atmmachine.exception.EntityNotFoundException;
import com.zinkworks.atmmachine.exception.ValidationException;
import com.zinkworks.atmmachine.notes.WithdrawalRequest;
import com.zinkworks.atmmachine.repository.ATMRepository;
import com.zinkworks.atmmachine.repository.UserAccountRepository;

/**
 * 
 * @author Manish.Singh
 *
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ATMServiceTest {

	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private ATMRepository atmRepository;
	@Autowired
	private ATMService atmService;

	@Test
	@Order(1)
	public void testInitialiseATM() {
		final ATM atm = new ATM(20, 30, 30, 10);
		atmRepository.save(atm);
		final ATM atmInitialized = atmService.findATM(atm.id);
		assertEquals(atm.getNote5(), atmInitialized.getNote5());
		assertEquals(atm.getNote10(), atmInitialized.getNote10());
		assertEquals(atm.getNote20(), atmInitialized.getNote20());
		assertEquals(atm.getNote50(), atmInitialized.getNote50());
	}

	@Test
	@Order(2)
	public void testAddAccountDetails() {
		final UserAccount account = new UserAccount(9833, 4321, 1234.0, 100.0);
		userAccountRepository.save(account);

		final UserAccount addedAccountDetails = atmService.addAccountDetails(account);
		assertEquals(account.getAccountNumber(), addedAccountDetails.getAccountNumber());
	}

	@Test
	@Order(3)
	public void testGetAccountBalanceWhenPinIncorrect() {
		final Exception exception = assertThrows(ValidationException.class, () -> {
			atmService.getAccountBalance(987654321L, 123);
		});

		assertTrue(exception.getMessage().equals("PIN is invalid, Please enter valid PIN"));
	}

	@Test
	@Order(4)
	public void testGetAccountBalanceWhenPinCorrect() {
		final AccountBalanceDTO accountBalance = atmService.getAccountBalance(987654321L, 4321);
		assertEquals(accountBalance.balance(), 1230);
	}

	@Test
	@Order(5)
	public void testDebitFromAccount() {
		Optional<UserAccount> accountOptional = userAccountRepository.findById(987654322L);
		if (accountOptional.isPresent()) {
			double beforeDebitAmount = accountOptional.get().getOpeningBalance();
			final WithdrawalRequest withdraw = new WithdrawalRequest(987654322, 1212, 100);
			final AccountBalanceDTO accountBalance = atmService.debitFromAccount(withdraw);
			assertEquals(beforeDebitAmount, accountBalance.balance() + 100);
		}
	}

	@Test
	@Order(6)
	public void testDebitLimitExceedATM() {
		final WithdrawalRequest withdraw = new WithdrawalRequest(987654321L, 4321, 1000000);
		final Exception exception = assertThrows(ValidationException.class, () -> {
			atmService.debitFromAccount(withdraw);
		});

		assertTrue(exception.getMessage().equals("Withdrawl amount limit exceed"));
	}

	@Test
	@Order(7)
	public void testWrongAccount() {
		final Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			atmService.findUserAccount(987654326L);
		});

		assertTrue(exception.getMessage().contains("User doesn't exist, please check account number"));
	}

}
