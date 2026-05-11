package com.neueda.test.atm.utils;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public enum Message {

	INSUFFICIENT_ATM_CASH("Insufficient cash in ATM"),

	INVALID_AMOUNT("Invalid amount. Amount should be multiple of "),

	INSUFFICIENT_ACCOUNT_BALANCE("Insufficient balance in account");

	private final String message;

	private Message(final String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
