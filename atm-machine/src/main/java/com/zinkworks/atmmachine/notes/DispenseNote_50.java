package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.FIFTY;

/**
 * @author Manish.Singh
 */
@Component("fifty")
public class DispenseNote_50 implements INoteDispenser {

    @Override
    public DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult) {
        final int noOf50NotesInATM = atmDetails.getNote50();
        int dispensedAmountLeft = dispenserResult.getAmtBalance();
        return dispense(atmDetails, dispenserResult, dispensedAmountLeft, noOf50NotesInATM, FIFTY);
    }
}