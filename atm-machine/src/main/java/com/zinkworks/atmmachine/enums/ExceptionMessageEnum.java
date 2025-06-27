package com.zinkworks.atmmachine.enums;

import lombok.Getter;

@Getter
public enum ExceptionMessageEnum {

    PIN_INVALID("PIN is invalid, Please enter valid PIN"),
    ENTITY_NOT_FOUND("oops! Something went wrong, doesnt exist"),
    WITHDRAWL_LIMIT_EXCEED("Withdrawl amount limit exceed"),
	ATM_HAS_NO_CASH("ATM has no cash, Try another day"),
    ATM_NOT_INITALISED("ATM is not initialized, call /atm with Currency Details"),
    USER_DOESNT_EXIST("User doesn't exist, please check account number");

    private final String message;

    ExceptionMessageEnum(String string) {
        this.message = string;
    }

}