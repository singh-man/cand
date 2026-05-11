package com.neueda.test.atm.utils;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public enum UrlConstants {
	
	ACCOUNT_SERVICE("http://ACCOUNT-SERVICE/accounts/"),
	
	DEBIT("debit"),
	
	CHECK_BALANCE("checkBalance/accountId/"),
	
	PIN("/pin/"),

	AMOUNT("/amount/");

	private final String url;

	private UrlConstants(final String url) {
		this.url = url;
	}

	public String value() {
		return url;
	}

}
