package com.neueda.test.atm.controller.errorHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionHandlerImpl {

	@ExceptionHandler(ValidationFailedException.class)
	public ResponseEntity<?> handleException(final ValidationFailedException exception) {
		final ATMServiceApiError apiError = new ATMServiceApiError(exception.getStatus(), exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> handleException(final HttpClientErrorException exception) {
		final ATMServiceApiError apiError = new ATMServiceApiError(exception.getStatusCode(), exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(final Exception exception) {
		final ATMServiceApiError apiError = new ATMServiceApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
