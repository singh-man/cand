package com.neueda.test.atm.utils;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public enum CurrencyValue {

	FIFTY(50),

	TWENTY(20),

	TEN(10),

	FIVE(5);

	private final Integer currencyValue;

	private CurrencyValue(final Integer currencyValue) {
		this.currencyValue = currencyValue;
	}

	public Integer value() {
		return currencyValue;
	}
	
	public static CurrencyValue getMinimumDenomination() {
		return FIVE;
	}

}
