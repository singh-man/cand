package com.zinkworks.atmmachine.controller.dto;

/**
 * @author Manish.Singh
 */
public record AccountDebitDTO(long accountId, int pin, double amount) {}