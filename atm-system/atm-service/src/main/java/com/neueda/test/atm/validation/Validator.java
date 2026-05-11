package com.neueda.test.atm.validation;

public interface Validator<T> {
	
	boolean isValid(T entity);
	
}
