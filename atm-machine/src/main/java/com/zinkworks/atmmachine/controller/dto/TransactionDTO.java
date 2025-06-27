package com.zinkworks.atmmachine.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Manish.Singh
 *
 */
@Getter
@Setter
public class TransactionDTO {

	private DispensedCashDTO dispensedCashDto;
	private AccountBalanceDTO accountBalanceDto;

}