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
public class TenCurrencyDispenser implements CurrencyDispenser {

	private CurrencyDispenser nextDispenser;

	@Override
	public DispenserResult dispense(final ATMCashDetails atmCashDetails, final DispenserResult dispenserResult) {
		log.debug("Running Ten currency dispenser, ATMCashDetails: {}, DispenserResult: {}", atmCashDetails,
				dispenserResult);

		final int noOfTenCurrencyInATM = atmCashDetails.getNoOfTenCurrency();
		int amountLeftTobeDispensed = dispenserResult.getAmountLeftTobeDispensed();
		if (amountLeftTobeDispensed >= CurrencyValue.TEN.value() && noOfTenCurrencyInATM >= 0) {
			final int numberToBeWithdrawn = amountLeftTobeDispensed / CurrencyValue.TEN.value();
			if (noOfTenCurrencyInATM >= numberToBeWithdrawn) {
				amountLeftTobeDispensed = amountLeftTobeDispensed % CurrencyValue.TEN.value();
				atmCashDetails.setNoOfTenCurrency(noOfTenCurrencyInATM - numberToBeWithdrawn);
				dispenserResult.getDispensedCashDetails().setNoOfTenCurrency(numberToBeWithdrawn);
			} else {
				amountLeftTobeDispensed = amountLeftTobeDispensed - noOfTenCurrencyInATM * CurrencyValue.TEN.value();
				dispenserResult.getDispensedCashDetails().setNoOfTenCurrency(noOfTenCurrencyInATM);
				atmCashDetails.setNoOfTenCurrency(0);
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
