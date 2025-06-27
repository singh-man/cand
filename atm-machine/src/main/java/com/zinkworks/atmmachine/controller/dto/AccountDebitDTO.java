package com.zinkworks.atmmachine.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Manish.Singh
 */
public record AccountDebitDTO(long accountId, int pin, double amount) {}