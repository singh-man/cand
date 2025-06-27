package com.zinkworks.atmmachine.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Manish.Singh
 */
public record AccountBalanceDTO(double balance, double overDraftBalance, double maximumWithdraw){}
