package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.controller.dto.DispensedCashDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Manish.Singh
 */
@AllArgsConstructor
@Data
public class DispenserResult {

	private DispensedCashDTO dispensedCashDTO;

	private int amtBalance;
}