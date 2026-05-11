package com.neueda.test.atm.controller.errorHandler;

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
public class ATMServiceApiError {

    private final HttpStatus status;
    
    private final String message;

}
