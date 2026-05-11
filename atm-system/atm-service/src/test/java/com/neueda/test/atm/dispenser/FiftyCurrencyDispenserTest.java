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
public class FiftyCurrencyDispenserTest {
	
	private FiftyCurrencyDispenser currencyDispenser;
	
	private ATMCashDetails atmCashDetails;
	
	private CurrencyDispenser nextDispenser;
	
	private DispensedCashDetails dispensedCashDetails;
	
	private DispenserResult dispenserResult;
	
	@BeforeEach
	public void setUp() {
		currencyDispenser = new FiftyCurrencyDispenser();
		dispensedCashDetails = new DispensedCashDetails();
		dispenserResult = new DispenserResult();
		dispenserResult.setDispensedCashDetails(dispensedCashDetails);
		nextDispenser = Mockito.mock(CurrencyDispenser.class);
	}

	@Test
	public void verifyNoOfFiftyDenominationDispensedWhenAmountWithdrawnIsGreaterThanFifty() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(65);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiftyCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfFiftyCurrency(), 9);
	}
	
	@Test
	public void verifyNoOfFiftyDenominationDispensedWhenAmountWithdrawnIsGreaterThanFiftyAndNotesInATMIsLess() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 1);
		dispenserResult.setAmountLeftTobeDispensed(100);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiftyCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfFiftyCurrency(), 0);
	}
	
	@Test
	public void verifyNoOfFiftyDenominationDispensedWhenAmountWithdrawnIsLessThanFifty() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(45);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiftyCurrency(), 0);
		assertEquals(atmCashDetails.getNoOfFiftyCurrency(), 10);
	}
	
	@Test
	public void verifyDenominationsWhenNextDispenserIsSet() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(60);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiftyCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfFiftyCurrency(), 9);
	}
	
	@Test
	public void verifyDenominationsWhenNextDispenserIsSetButAmountLeftIsZero() {
		atmCashDetails = new ATMCashDetails(10, 10, 10, 10);
		dispenserResult.setAmountLeftTobeDispensed(50);
		currencyDispenser.setNextDispenser(nextDispenser);
		currencyDispenser.dispense(atmCashDetails, dispenserResult);
		assertEquals(dispenserResult.getDispensedCashDetails().getNoOfFiftyCurrency(), 1);
		assertEquals(atmCashDetails.getNoOfFiftyCurrency(), 9);
	}

}
