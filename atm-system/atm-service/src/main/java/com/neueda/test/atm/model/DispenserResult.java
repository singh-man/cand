package com.neueda.test.atm.model;

import com.neueda.test.atm.VO.DispensedCashDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispenserResult {
	
	private DispensedCashDetails dispensedCashDetails;
	
	private int amountLeftTobeDispensed;

}
