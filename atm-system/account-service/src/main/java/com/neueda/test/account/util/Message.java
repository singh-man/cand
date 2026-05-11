package com.neueda.test.account.util;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public enum Message {

	INCORRECT_PIN("Incorrect pin");
	
	private final String message;

	private Message(final String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
