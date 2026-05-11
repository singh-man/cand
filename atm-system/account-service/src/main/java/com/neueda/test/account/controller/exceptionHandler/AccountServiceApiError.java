package com.neueda.test.account.controller.exceptionHandler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Anubhav.Anand
 *
 */
@Data
@AllArgsConstructor
public class AccountServiceApiError {

    private final HttpStatus status;
    
    private final String message;
}
