package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.TEN;

@Component("ten")
public class DispenseNote_10 implements INoteDispenser {

    @Override
    public DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult) {
        final int noOf10NotesInATM = atmDetails.getNote10();
        int dispensedAmountLeft = dispenserResult.getAmtBalance();
        if (dispensedAmountLeft >= TEN.value() && noOf10NotesInATM >= 0) {
            final int withdrawNumber = dispensedAmountLeft / TEN.value();
            if (noOf10NotesInATM >= withdrawNumber) {
                dispensedAmountLeft = dispensedAmountLeft % TEN.value();
                atmDetails.setNote10(noOf10NotesInATM - withdrawNumber);
                dispenserResult.getDispensedCashDTO().setNote10(withdrawNumber);
            } else {
                dispensedAmountLeft = dispensedAmountLeft - noOf10NotesInATM * TEN.value();
                dispenserResult.getDispensedCashDTO().setNote10(noOf10NotesInATM);
                atmDetails.setNote10(0);
            }
            dispenserResult.setAmtBalance(dispensedAmountLeft);
        }
        return dispenserResult;
    }
}