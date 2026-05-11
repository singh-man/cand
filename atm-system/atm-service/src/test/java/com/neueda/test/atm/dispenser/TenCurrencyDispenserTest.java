package com.neueda.test.atm.dispenser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.neueda.test.atm.VO.DispensedCashDetails;
import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.DispenserResult;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public class TenCurrencyDispenserTest {

	private TenCurrencyDispenser currencyDispenser;

	private ATMCashDetails atmCashDetails;

	private CurrencyDispenser nextDispenser;

	private DispensedCashDetails dispensedCashDetails;

	private DispenserResult dispenserResult;

	@BeforeEach
	public void setUp() {
		currencyDispenser = new TenCurrencyDispenser();
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispensedCashDetails = new DispensedCashDetails();
		dispenserResult = new DispenserResult();
		dispenserResult.setDispensedCashDetails(dispensedCashDetails);
		nextDispenser = Mockito.mock(CurrencyDispenser.class);
	}

	@Test
	public void verifyNoOfTenDenominationDispensedWhenAmountWithdrawnIsGreaterThanTen() {
		dispenserResult.setAmountLeftTobeDispensed(22);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTenCurrency(), 2);
		assertEquals(atmCashDetails.getNoOfTenCurrency(), 8);
	}

	@Test
	public void verifyNoOfTenDenominationDispensedWhenAmountWithdrawnIsGreaterThanTenAndNotesInATMIsLess() {
		atmCashDetails = new ATMCashDetails(10, 1, 10, 1);
		dispenserResult.setAmountLeftTobeDispensed(20);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTenCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfTenCurrency(), 0);
	}

	@Test
	public void verifyNoOfTenDenominationDispensedWhenAmountWithdrawnIsLessThanTen() {
		dispenserResult.setAmountLeftTobeDispensed(5);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTenCurrency(), 0);
		assertEquals(atmCashDetails.getNoOfTenCurrency(), 10);
	}

	@Test
	public void verifyDenominationsWhenNextDispenserIsSet() {
		dispenserResult.setAmountLeftTobeDispensed(25);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTenCurrency(), 2);
		assertEquals(atmCashDetails.getNoOfTenCurrency(), 8);
	}

	@Test
	public void verifyDenominationsWhenNextDispenserIsSetButAmountLeftIsZero() {
		dispenserResult.setAmountLeftTobeDispensed(10);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTenCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfTenCurrency(), 9);
	}

}
