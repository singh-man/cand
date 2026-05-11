package com.neueda.test.atm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class ATMCashDetails {
	
	@Id
	@ApiModelProperty(required = false, hidden = true)
	public final Long id = 1L;

	private Integer noOfFiveCurrency;
	
	private Integer noOfTenCurrency;
	
	private Integer noOfTwentyCurrency;
	
	private Integer noOfFiftyCurrency;
	
}
