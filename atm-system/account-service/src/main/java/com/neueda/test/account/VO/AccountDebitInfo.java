package com.neueda.test.account.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountDebitInfo {
	
	private long accountId;
	
	@ToString.Exclude
	private int pin;
	
	private double amount;

}
