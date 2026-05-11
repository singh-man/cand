package com.neueda.test.account.controller.exceptionHandler;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Anubhav.Anand
 *
 */
public class ValidationFailedException extends RuntimeException{

	private static final long serialVersionUID = 1881378939117780703L;
	private HttpStatus status;
	
	public ValidationFailedException(final HttpStatus status, final String message){
		super(message);
		this.setStatus(status);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
