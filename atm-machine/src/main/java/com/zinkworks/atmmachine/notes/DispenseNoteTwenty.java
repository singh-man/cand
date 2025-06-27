package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.TWENTY;

/**
 * 
 * @author Manish.Singh
 *
 */
@Component("twenty")
public class DispenseNoteTwenty implements NoteDispenser {

	private NoteDispenser nextDispenser;

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

		if (dispensedAmountLeft > 0 && nextDispenser != null) {
			return nextDispenser.dispense(atmDetails, dispenserResult);
		}

		return dispenserResult;
	}

	@Override
	public void nextDispenser(final NoteDispenser nextDispenser) {
		this.nextDispenser = nextDispenser;
	}

	@Override
	public DispenserResult_2 dispense(DispenserResult_2 dispeneserResult2) {
		ATM atmDetails = dispeneserResult2.atm();
		DispenserResult dispenserResult = dispeneserResult2.result();
		int amtBal = dispenserResult.getAmtBalance();
		if (amtBal > 0) {
			dispense(amtBal,
					TWENTY,
					() -> atmDetails.getNote20(),
					notesInATM -> atmDetails.setNote20(notesInATM),
					(notesNeeded, amtBalance) -> {
						dispenserResult.getDispensedCashDTO().setNote20(notesNeeded);
						dispenserResult.setAmtBalance(amtBalance);
					});
		}
		return dispeneserResult2;
	}
}
