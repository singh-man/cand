package com.neueda.test.atm.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TransactionDetails {
	
	private DispensedCashDetails dispensedCashDetails;
	
	private AccountBalance accountBalance;

}
