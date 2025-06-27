package com.zinkworks.atmmachine.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerImpl {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleException(final ValidationException exception) {
		final ATMApiError apiError = new ATMApiError(exception.getStatus(), exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(final Exception exception) {
		final ATMApiError apiError = new ATMApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
