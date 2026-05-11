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
public class TwentyCurrencyDispenserTest {

	private TwentyCurrencyDispenser currencyDispenser;

	private ATMCashDetails atmCashDetails;

	private CurrencyDispenser nextDispenser;

	private DispensedCashDetails dispensedCashDetails;

	private DispenserResult dispenserResult;

	@BeforeEach
	public void setUp() {
		currencyDispenser = new TwentyCurrencyDispenser();
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispensedCashDetails = new DispensedCashDetails();
		dispenserResult = new DispenserResult();
		dispenserResult.setDispensedCashDetails(dispensedCashDetails);
		nextDispenser = Mockito.mock(CurrencyDispenser.class);
	}

	@Test
	public void verifyNoOfTwentyDenominationDispensedWhenAmountWithdrawnIsGreaterThanTwenty() {
		dispenserResult.setAmountLeftTobeDispensed(60);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTwentyCurrency(), 3);
		assertEquals(atmCashDetails.getNoOfTwentyCurrency(), 7);
	}

	@Test
	public void verifyNoOfTwentyDenominationDispensedWhenAmountWithdrawnIsGreaterThanTwentyAndNotesInATMIsLess() {
		atmCashDetails = new ATMCashDetails(10, 10, 1, 10);
		dispenserResult.setAmountLeftTobeDispensed(40);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTwentyCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfTwentyCurrency(), 0);
	}

	@Test
	public void verifyNoOfTwentyDenominationDispensedWhenAmountWithdrawnIsLessThanTwenty() {
		dispenserResult.setAmountLeftTobeDispensed(10);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTwentyCurrency(), 0);
		assertEquals(atmCashDetails.getNoOfTwentyCurrency(), 10);
	}

	@Test
	public void verifyDenominationsWhenNextDispenserIsSet() {
		dispenserResult.setAmountLeftTobeDispensed(65);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTwentyCurrency(), 3);
		assertEquals(atmCashDetails.getNoOfTwentyCurrency(), 7);
	}

	@Test
	public void verifyDenominationsWhenNextDispenserIsSetButAmountLeftIsZero() {
		dispenserResult.setAmountLeftTobeDispensed(20);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfTwentyCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfTwentyCurrency(), 9);
	}

}
