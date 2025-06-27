package com.zinkworks.atmmachine.enums;

/**
 * 
 * @author Manish.Singh
 *
 */
public enum CurrencyEnum {

	FIFTY(50),

	TWENTY(20),

	TEN(10),

	FIVE(5);

	private final Integer value;

	CurrencyEnum(final Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}

	public static CurrencyEnum getMinimum() {
		return FIVE;
	}

}
