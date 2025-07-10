package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.TWENTY;

/**
 * @author Manish.Singh
 */
@Component("twenty")
public class DispenseNote_20 implements INoteDispenser {

	@Override
	public DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult) {
		final int noOf20NotesInATM = atmDetails.getNote20();
		int dispensedAmountLeft = dispenserResult.getAmtBalance();
		if (dispensedAmountLeft >= TWENTY.value() && noOf20NotesInATM >= 0) {
			final int withdrawNumber = dispensedAmountLeft / TWENTY.value();
			if (noOf20NotesInATM >= withdrawNumber) {
				dispensedAmountLeft = dispensedAmountLeft % TWENTY.value();
				atmDetails.setNote20(noOf20NotesInATM - withdrawNumber);
				dispenserResult.getDispensedCashDTO().setNote20(withdrawNumber);
			} else {
				dispensedAmountLeft = dispensedAmountLeft - noOf20NotesInATM * TWENTY.value();
				dispenserResult.getDispensedCashDTO().setNote20(noOf20NotesInATM);
				atmDetails.setNote20(0);
			}
			dispenserResult.setAmtBalance(dispensedAmountLeft);
		}
		return dispenserResult;
	}
}