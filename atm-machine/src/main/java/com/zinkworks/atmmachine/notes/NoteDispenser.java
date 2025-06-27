package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.enums.CurrencyEnum;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 
 * @author Manish.Singh
 *
 */
public interface NoteDispenser {

	void nextDispenser(NoteDispenser nextDispenser);

	DispenserResult dispense(final ATM atmDetails, final DispenserResult dispenserResult);

	DispenserResult_2 dispense(DispenserResult_2 dispeneserResult2);

	private int withdrawNotes(int amtLeft, CurrencyEnum curr, Supplier<Integer> notesLeft) {
		int notesNeeded = amtLeft / curr.value();
		return Optional.of(notesNeeded)
				.filter(e -> e <= notesLeft.get())
				.orElse(notesLeft.get());
	}

	default void dispense(Integer amtBalance, CurrencyEnum curr, Supplier<Integer> notesInATM, Consumer<Integer> setNotesInATM, BiConsumer<Integer, Integer> dis) {
		int notesNeeded = withdrawNotes(amtBalance, curr, notesInATM);
		setNotesInATM.accept(notesInATM.get() - notesNeeded);
		amtBalance -= notesNeeded * curr.value();
		dis.accept(notesNeeded, amtBalance);
	}
}
