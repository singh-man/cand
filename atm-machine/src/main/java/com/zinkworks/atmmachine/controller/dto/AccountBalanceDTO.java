package com.zinkworks.atmmachine.controller.dto;

/**
 * @author Manish.Singh
 */
public record AccountBalanceDTO(double balance, double overDraftBalance, double maximumWithdraw){}
