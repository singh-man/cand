package com.neueda.test.atm.utils;

public class AmountBuilder {
	
	private int noOfFiftyCurrency;
	
	private int noOfTwentyCurrency;
	
	private int noOfTenCurrency;
	
	private int noOfFiveCurrency;
	
	public AmountBuilder setNoOfFiftyCurrency(final int noOfFiftyCurrency) {
		this.noOfFiftyCurrency = noOfFiftyCurrency;
		return this;
	}
	
	public AmountBuilder setNoOfTwentyCurrency(final int noOfTwentyCurrency) {
		this.noOfTwentyCurrency = noOfTwentyCurrency;
		return this;
	}
	
	public AmountBuilder setNoOfTenCurrency(final int noOfTenCurrency) {
		this.noOfTenCurrency = noOfTenCurrency;
		return this;
	}
	
	public AmountBuilder setNoOfFiveCurrency(final int noOfFiveCurrency) {
		this.noOfFiveCurrency = noOfFiveCurrency;
		return this;
	}
	
	public Integer getTotalAmount() {
		return noOfFiftyCurrency * CurrencyValue.FIFTY.value()
				+ noOfTwentyCurrency * CurrencyValue.TWENTY.value()
				+ noOfTenCurrency * CurrencyValue.TEN.value()
				+ noOfFiveCurrency * CurrencyValue.FIVE.value();
	}
	
}
 