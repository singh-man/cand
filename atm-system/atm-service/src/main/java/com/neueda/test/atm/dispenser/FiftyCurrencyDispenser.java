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
public class FiftyCurrencyDispenser implements CurrencyDispenser {

	private CurrencyDispenser nextDispenser;

	@Override
	public DispenserResult dispense(final ATMCashDetails atmCashDetails, final DispenserResult dispenserResult) {
		log.debug("Running Fify currency dispenser, ATMCashDetails: {}, DispenserResult: {}", atmCashDetails,
				dispenserResult);
		final int noOfFiftyCurrencyInATM = atmCashDetails.getNoOfFiftyCurrency();
		int amountLeftTobeDispensed = dispenserResult.getAmountLeftTobeDispensed();
		if (amountLeftTobeDispensed >= CurrencyValue.FIFTY.value() && noOfFiftyCurrencyInATM >= 0) {
			final int numberToBeWithdrawn = amountLeftTobeDispensed / CurrencyValue.FIFTY.value();
			if (noOfFiftyCurrencyInATM >= numberToBeWithdrawn) {
				amountLeftTobeDispensed = amountLeftTobeDispensed % CurrencyValue.FIFTY.value();
				atmCashDetails.setNoOfFiftyCurrency(noOfFiftyCurrencyInATM - numberToBeWithdrawn);
				dispenserResult.getDispensedCashDetails().setNoOfFiftyCurrency(numberToBeWithdrawn);
			} else {
				amountLeftTobeDispensed = amountLeftTobeDispensed
						- noOfFiftyCurrencyInATM * CurrencyValue.FIFTY.value();
				dispenserResult.getDispensedCashDetails().setNoOfFiftyCurrency(noOfFiftyCurrencyInATM);
				atmCashDetails.setNoOfFiftyCurrency(0);
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
