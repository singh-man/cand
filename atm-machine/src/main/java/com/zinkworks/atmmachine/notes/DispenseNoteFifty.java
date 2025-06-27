package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.enums.CurrencyEnum;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.FIFTY;

/**
 * 
 * @author Manish.Singh
 *
 */
@Component("fifty")
public class DispenseNoteFifty implements NoteDispenser {

	private NoteDispenser nextDispenser;

	@Override
	public DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult) {
		final int noOf50NotesInATM = atmDetails.getNote50();
		int dispensedAmountLeft = dispenserResult.getAmtBalance();
		if (dispensedAmountLeft >= FIFTY.value() && noOf50NotesInATM >= 0) {
			final int withdrawNumber = dispensedAmountLeft / CurrencyEnum.FIFTY.value();
			if (noOf50NotesInATM >= withdrawNumber) {
				dispensedAmountLeft = dispensedAmountLeft % CurrencyEnum.FIFTY.value();
				atmDetails.setNote50(noOf50NotesInATM - withdrawNumber);
				dispenserResult.getDispensedCashDTO().setNote50(withdrawNumber);
			} else {
				dispensedAmountLeft = dispensedAmountLeft - noOf50NotesInATM * CurrencyEnum.FIFTY.value();
				dispenserResult.getDispensedCashDTO().setNote50(noOf50NotesInATM);
				atmDetails.setNote50(0);
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
					FIFTY,
					() -> atmDetails.getNote50(),
					notesInATM -> atmDetails.setNote50(notesInATM),
					(notesNeeded, amtBalance) -> {
						dispenserResult.getDispensedCashDTO().setNote50(notesNeeded);
						dispenserResult.setAmtBalance(amtBalance);
					});
		}
		return dispeneserResult2;
	}
}
