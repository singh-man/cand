package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.enums.CurrencyEnum;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.TEN;

@Component("ten")
public class DispenseNoteTen implements NoteDispenser {

	private NoteDispenser nextDispenser;

	@Override
	public DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult) {
		final int noOf10NotesInATM = atmDetails.getNote10();
		int dispensedAmountLeft = dispenserResult.getAmtBalance();
		if (dispensedAmountLeft >= TEN.value() && noOf10NotesInATM >= 0) {
			final int withdrawNumber = dispensedAmountLeft / CurrencyEnum.TEN.value();
			if (noOf10NotesInATM >= withdrawNumber) {
				dispensedAmountLeft = dispensedAmountLeft % CurrencyEnum.TEN.value();
				atmDetails.setNote10(noOf10NotesInATM - withdrawNumber);
				dispenserResult.getDispensedCashDTO().setNote10(withdrawNumber);
			} else {
				dispensedAmountLeft = dispensedAmountLeft - noOf10NotesInATM * CurrencyEnum.TEN.value();
				dispenserResult.getDispensedCashDTO().setNote10(noOf10NotesInATM);
				atmDetails.setNote10(0);
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
					TEN,
					() -> atmDetails.getNote10(),
					notesInATM -> atmDetails.setNote10(notesInATM),
					(notesNeeded, amtBalance) -> {
						dispenserResult.getDispensedCashDTO().setNote10(notesNeeded);
						dispenserResult.setAmtBalance(amtBalance);
					});
		}
		return dispeneserResult2;
	}
}
