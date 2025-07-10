package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.enums.CurrencyEnum;

/**
 * @author Manish.Singh
 */
public interface INoteDispenser {

    DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult);

    default DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult,
                                     int dispensedAmountLeft, int notes, CurrencyEnum curr) {
        if (dispensedAmountLeft >= curr.value() && notes >= 0) {
            final int withdrawNumber = dispensedAmountLeft / curr.value();
            if (notes >= withdrawNumber) {
                dispensedAmountLeft = dispensedAmountLeft % curr.value();
                atmDetails.setNote50(notes - withdrawNumber);
                dispenserResult.getDispensedCashDTO().setNote50(withdrawNumber);
            } else {
                dispensedAmountLeft = dispensedAmountLeft - notes * curr.value();
                dispenserResult.getDispensedCashDTO().setNote50(notes);
                atmDetails.setNote50(0);
            }
            dispenserResult.setAmtBalance(dispensedAmountLeft);
        }
        return dispenserResult;
    }

}