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
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AccountBalance {
	
	private double regularBalance;
	
	private double overDraftBalance;
	
	private double maxWithdrawalAmount;
}
