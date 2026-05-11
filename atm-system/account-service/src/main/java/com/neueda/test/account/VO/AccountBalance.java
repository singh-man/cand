package com.neueda.test.account.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountBalance {
	
	private double regularBalance;
	
	private double overDraftBalance;
	
	private double maxWithdrawalAmount;
}
