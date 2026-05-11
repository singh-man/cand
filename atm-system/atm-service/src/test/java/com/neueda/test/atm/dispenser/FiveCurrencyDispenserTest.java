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
public class FiveCurrencyDispenserTest {

	private FiveCurrencyDispenser currencyDispenser;

	private ATMCashDetails atmCashDetails;

	private CurrencyDispenser nextDispenser;

	private DispensedCashDetails dispensedCashDetails;

	private DispenserResult dispenserResult;

	@BeforeEach
	public void setUp() {
		currencyDispenser = new FiveCurrencyDispenser();
		dispensedCashDetails = new DispensedCashDetails();
		dispenserResult = new DispenserResult();
		dispenserResult.setDispensedCashDetails(dispensedCashDetails);
		nextDispenser = Mockito.mock(CurrencyDispenser.class);
	}

	@Test
	public void verifyNoOfFiveDenominationDispensedWhenAmountWithdrawnIsGreaterThanFive() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(12);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiveCurrency(), 2);
		assertEquals(atmCashDetails.getNoOfFiveCurrency(), 8);
	}

	@Test
	public void verifyNoOfFiveDenominationDispensedWhenAmountWithdrawnIsGreaterThanFiveAndNotesInATMIsLess() {
		atmCashDetails = new ATMCashDetails(1, 10, 10, 1);
		dispenserResult.setAmountLeftTobeDispensed(10);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiveCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfFiveCurrency(), 0);
	}

	@Test
	public void verifyNoOfFiveDenominationDispensedWhenAmountWithdrawnIsLessThanFive() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(2);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiveCurrency(), 0);
		assertEquals(atmCashDetails.getNoOfFiveCurrency(), 10);
	}

	@Test
	public void verifyDenominationsWhenNextDispenserIsSet() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(17);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiveCurrency(), 3);
		assertEquals(atmCashDetails.getNoOfFiveCurrency(), 7);
	}

	@Test
	public void verifyDenominationsWhenNextDispenserIsSetButAmountLeftIsZero() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(10);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiveCurrency(), 2);
		assertEquals(atmCashDetails.getNoOfFiveCurrency(), 8);
	}

}
