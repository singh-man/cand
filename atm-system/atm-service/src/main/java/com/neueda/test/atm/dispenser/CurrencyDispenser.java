package com.neueda.test.atm.dispenser;

import com.neueda.test.atm.entity.ATMCashDetails;
import com.neueda.test.atm.model.DispenserResult;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public interface CurrencyDispenser {
	
	DispenserResult dispense(ATMCashDetails atmDetails, DispenserResult dispenserResult);
	
	void setNextDispenser(CurrencyDispenser nextDispenser);

}
