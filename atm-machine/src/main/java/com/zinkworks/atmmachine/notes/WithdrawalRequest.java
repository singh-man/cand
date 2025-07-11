package com.zinkworks.atmmachine.notes;

public record WithdrawalRequest(long accountId, int pin, int amount) { }