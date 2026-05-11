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
public class TwentyCurrencyDispenser implements CurrencyDispenser {

	private CurrencyDispenser nextDispenser;

	@Override
	public DispenserResult dispense(final ATMCashDetails atmCashDetails, final DispenserResult dispenserResult) {
		log.debug("Running Ten currency dispenser, ATMCashDetails: {}, TransactionDetails: {}",
				atmCashDetails, dispenserResult);

		final int noOfTwentyCurrencyInATM = atmCashDetails.getNoOfTwentyCurrency();
		int amountLeftTobeDispensed = dispenserResult.getAmountLeftTobeDispensed();
		if (amountLeftTobeDispensed >= CurrencyValue.TWENTY.value() && noOfTwentyCurrencyInATM >= 0) {
			final int numberToBeWithdrawn = amountLeftTobeDispensed / CurrencyValue.TWENTY.value();
			if (noOfTwentyCurrencyInATM >= numberToBeWithdrawn) {
				amountLeftTobeDispensed = amountLeftTobeDispensed % CurrencyValue.TWENTY.value();
				atmCashDetails.setNoOfTwentyCurrency(noOfTwentyCurrencyInATM - numberToBeWithdrawn);
				dispenserResult.getDispensedCashDetails().setNoOfTwentyCurrency(numberToBeWithdrawn);
			} else {
				amountLeftTobeDispensed = amountLeftTobeDispensed - noOfTwentyCurrencyInATM * CurrencyValue.TWENTY.value();
				dispenserResult.getDispensedCashDetails().setNoOfTwentyCurrency(noOfTwentyCurrencyInATM);
				atmCashDetails.setNoOfTwentyCurrency(0);
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
