package com.neueda.test.atm.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.neueda.test.atm.controller.errorHandler.ValidationFailedException;
import com.neueda.test.atm.model.WithdrawalRequest;
import com.neueda.test.atm.utils.CurrencyValue;
import com.neueda.test.atm.utils.Message;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public class InvalidWithdrwalAmountValidatorTest {

    private InvalidWithdrwalAmountValidator validator  = new InvalidWithdrwalAmountValidator();
    
    @Test
    public void testIsValidWhenAmountIsInvalid() {
        final WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setAmount(12);
        final ValidationFailedException exception = assertThrows(ValidationFailedException.class, () -> {
            validator.isValid(withdrawalRequest);
        });
        assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getMessage(), Message.INVALID_AMOUNT.message() + CurrencyValue.getMinimumDenomination().value());

    }

    @Test
    public void testIsValidWhenAmountIsValid() {
        final WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        withdrawalRequest.setAmount(10);
        assertTrue(validator.isValid(withdrawalRequest));
    }
}
