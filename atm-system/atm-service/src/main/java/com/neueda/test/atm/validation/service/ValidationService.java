package com.neueda.test.atm.validation.service;


public interface ValidationService<T> {
	
	public boolean validate(T request);

}
