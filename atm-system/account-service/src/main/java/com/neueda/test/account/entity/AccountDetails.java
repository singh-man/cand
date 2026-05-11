package com.neueda.test.account.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AccountDetails {
	
	@Id
	private long accountId;
	
	@ToString.Exclude
	private int pin;
	
	private double openingBalance;
	
	private double overDraft;

}
