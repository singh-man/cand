package com.neueda.test.atm.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispensedCashDetails {

	private int noOfFiveCurrency;

	private int noOfTenCurrency;

	private int noOfTwentyCurrency;

	private int noOfFiftyCurrency;

}
