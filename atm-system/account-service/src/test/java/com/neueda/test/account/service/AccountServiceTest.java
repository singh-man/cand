package com.neueda.test.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.neueda.test.account.VO.AccountBalance;
import com.neueda.test.account.VO.AccountDebitInfo;
import com.neueda.test.account.controller.exceptionHandler.ValidationFailedException;
import com.neueda.test.account.entity.AccountDetails;
import com.neueda.test.account.repository.AccountRepository;

/**
 * 
 * @author Anubhav.Anand
 *
 */
class AccountServiceTest {
	
	private AccountRepository accountRepository;
	
	private AccountService accountService;
	
	@BeforeEach
	public void setup() {
		accountRepository = Mockito.mock(AccountRepository.class);
		accountService = new AccountService(accountRepository);
	}

	@Test
	public void testAddAccountDetails() {
		final AccountDetails accountDetails = new AccountDetails(987654321, 4321, 1230.0, 150.0);
		when(accountRepository.save(ArgumentMatchers.any())).thenReturn(accountDetails);
		
		final AccountDetails addedAccountDetails = accountService.addAccountDetails(accountDetails);
		assertEquals(accountDetails, addedAccountDetails);
	}
	
	@Test
	public void testGetAccountBalanceWhenPinIncorrect() {
		final AccountDetails accountDetails = new AccountDetails(987654321, 4321, 1230.0, 150.0);
		when(accountRepository.getById(ArgumentMatchers.any())).thenReturn(accountDetails);
		
		final Exception exception = assertThrows(ValidationFailedException.class, () -> {
			accountService.getAccountBalance(987654321L, 123);
	    });

	    assertTrue(exception.getMessage().contains("Incorrect pin"));
	}
	
	@Test
	public void testGetAccountBalanceWhenPinCorrect() {
		final AccountDetails accountDetails = new AccountDetails(987654321, 4321, 1230.0, 150.0);
		when(accountRepository.getById(ArgumentMatchers.any())).thenReturn(accountDetails);
		
		final AccountBalance accountBalance = accountService.getAccountBalance(987654321L, 4321);
		
	    assertEquals(accountBalance, new AccountBalance(1230.0, 150.0, 1380.0));
	}
	
	@Test
	public void testDebitFromAccountWhenRegularBalanceIsSufficient() {
		final AccountDetails accountDetails = new AccountDetails(987654321, 4321, 1230.0, 150.0);
		when(accountRepository.getById(ArgumentMatchers.any())).thenReturn(accountDetails);
		
		final AccountDebitInfo accountDebitInfo = new AccountDebitInfo(987654321, 4321, 100.0);		
		final AccountBalance accountBalance = accountService.debitFromAccount(accountDebitInfo);
		
	    assertEquals(accountBalance, new AccountBalance(1130.0, 150.0, 1280.0));
	}
	
	@Test
	public void testDebitFromAccountWhenOverdraftIsUsed() {
		final AccountDetails accountDetails = new AccountDetails(987654321, 4321, 1230.0, 150.0);
		when(accountRepository.getById(ArgumentMatchers.any())).thenReturn(accountDetails);
		
		final AccountDebitInfo accountDebitInfo = new AccountDebitInfo(987654321, 4321, 1280.0);		
		final AccountBalance accountBalance = accountService.debitFromAccount(accountDebitInfo);
		
	    assertEquals(accountBalance, new AccountBalance(0.0, 100.0, 100.0));
	}
	
	@Test
	public void testDebitFromAccountWhenOverdraftIsCompletelyUsed() {
		final AccountDetails accountDetails = new AccountDetails(987654321, 4321, 1230.0, 150.0);
		when(accountRepository.getById(ArgumentMatchers.any())).thenReturn(accountDetails);
		
		final AccountDebitInfo accountDebitInfo = new AccountDebitInfo(987654321, 4321, 1380.0);		
		final AccountBalance accountBalance = accountService.debitFromAccount(accountDebitInfo);
		
	    assertEquals(accountBalance, new AccountBalance(0.0, 0.0, 0.0));
	}

}
