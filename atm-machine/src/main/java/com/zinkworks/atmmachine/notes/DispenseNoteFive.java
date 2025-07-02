package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.enums.CurrencyEnum;
import org.springframework.stereotype.Component;

import static com.zinkworks.atmmachine.enums.CurrencyEnum.FIVE;

/**
 * 
 * @author Manish.Singh
 *
 */
@Component("five")
public class DispenseNoteFive implements INoteDispenser {

	private INoteDispenser nextDispenser;

	@Override
	public DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult) {
		final int noOf5NotesInATM = atmDetails.getNote5();
		int dispensedAmountLeft = dispenserResult.getAmtBalance();
		if (dispensedAmountLeft >= FIVE.value() && noOf5NotesInATM >= 0) {
			final int withdrawNumber = dispensedAmountLeft / FIVE.value();
			if (noOf5NotesInATM >= withdrawNumber) {
				dispensedAmountLeft = dispensedAmountLeft % CurrencyEnum.FIVE.value();
				atmDetails.setNote5(noOf5NotesInATM - withdrawNumber);
				dispenserResult.getDispensedCashDTO().setNote5(withdrawNumber);
			} else {
				dispensedAmountLeft = dispensedAmountLeft - noOf5NotesInATM * CurrencyEnum.FIVE.value();
				dispenserResult.getDispensedCashDTO().setNote5(noOf5NotesInATM);
				atmDetails.setNote5(0);
			}
			dispenserResult.setAmtBalance(dispensedAmountLeft);
		}

		if (dispensedAmountLeft > 0 && nextDispenser != null) {
			return nextDispenser.dispense(atmDetails, dispenserResult);
		}

		return dispenserResult;
	}

	@Override
	public void nextDispenser(final INoteDispenser nextDispenser) {
		this.nextDispenser = nextDispenser;
	}

	@Override
	public DispenserResult_2 dispense(DispenserResult_2 dispeneserResult2) {
		ATM atmDetails = dispeneserResult2.atm();
		DispenserResult dispenserResult = dispeneserResult2.result();
		int amtBal = dispenserResult.getAmtBalance();
		if (amtBal > 0) {
			dispense(amtBal,
					FIVE,
					() -> atmDetails.getNote5(),
					notesInATM -> atmDetails.setNote5(notesInATM),
					(notesNeeded, amtBalance) -> {
						dispenserResult.getDispensedCashDTO().setNote5(notesNeeded);
						dispenserResult.setAmtBalance(amtBalance);
					});
		}
		return dispeneserResult2;
	}
}
