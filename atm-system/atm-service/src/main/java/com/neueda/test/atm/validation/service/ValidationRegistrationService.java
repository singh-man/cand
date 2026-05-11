package com.neueda.test.atm.validation.service;

import com.neueda.test.atm.validation.Validator;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public interface ValidationRegistrationService<T> {
	
	void register(final Validator<T> validator);

}
