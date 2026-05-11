package com.neueda.test.atm.dispenser;

import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.DispenserResult;
import com.neueda.test.atm.utils.CurrencyValue;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Slf4j
public class FiveCurrencyDispenser implements CurrencyDispenser {

	private CurrencyDispenser nextDispenser;

	@Override
	public DispenserResult dispense(final ATMCashDetails atmCashDetails, final DispenserResult dispenserResult) {
		log.debug("Running Five currency dispenser, ATMCashDetails: {}, DispenserResult: {}", atmCashDetails,
				dispenserResult);

		final int noOfFiveCurrencyInATM = atmCashDetails.getNoOfFiveCurrency();
		int amountLeftTobeDispensed = dispenserResult.getAmountLeftTobeDispensed();
		if (amountLeftTobeDispensed >= CurrencyValue.FIVE.value() && noOfFiveCurrencyInATM >= 0) {
			final int numberToBeWithdrawn = amountLeftTobeDispensed / CurrencyValue.FIVE.value();
			if (noOfFiveCurrencyInATM >= numberToBeWithdrawn) {
				amountLeftTobeDispensed = amountLeftTobeDispensed % CurrencyValue.FIVE.value();
				atmCashDetails.setNoOfFiveCurrency(noOfFiveCurrencyInATM - numberToBeWithdrawn);
				dispenserResult.getDispensedCashDetails().setNoOfFiveCurrency(numberToBeWithdrawn);
			} else {
				amountLeftTobeDispensed = amountLeftTobeDispensed - noOfFiveCurrencyInATM * CurrencyValue.FIVE.value();
				dispenserResult.getDispensedCashDetails().setNoOfFiveCurrency(noOfFiveCurrencyInATM);
				atmCashDetails.setNoOfFiveCurrency(0);
			}
			dispenserResult.setAmountLeftTobeDispensed(amountLeftTobeDispensed);
		}

		if (amountLeftTobeDispensed > 0 && nextDispenser != null) {
			return nextDispenser.dispense(atmCashDetails, dispenserResult);
		}

		return dispenserResult;
	}

	@Override
	public void setNextDispenser(final CurrencyDispenser nextDispenser) {
		this.nextDispenser = nextDispenser;
	}

}
