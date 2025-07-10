package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.FIVE;

/**
 * @author Manish.Singh
 */
@Component("five")
public class DispenseNote_5 implements INoteDispenser {

    @Override
    public DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult) {
        final int noOf5NotesInATM = atmDetails.getNote5();
        int dispensedAmountLeft = dispenserResult.getAmtBalance();
        if (dispensedAmountLeft >= FIVE.value() && noOf5NotesInATM >= 0) {
            final int withdrawNumber = dispensedAmountLeft / FIVE.value();
            if (noOf5NotesInATM >= withdrawNumber) {
                dispensedAmountLeft = dispensedAmountLeft % FIVE.value();
                atmDetails.setNote5(noOf5NotesInATM - withdrawNumber);
                dispenserResult.getDispensedCashDTO().setNote5(withdrawNumber);
            } else {
                dispensedAmountLeft = dispensedAmountLeft - noOf5NotesInATM * FIVE.value();
                dispenserResult.getDispensedCashDTO().setNote5(noOf5NotesInATM);
                atmDetails.setNote5(0);
            }
            dispenserResult.setAmtBalance(dispensedAmountLeft);
        }
        return dispenserResult;
    }
}