package com.neueda.test.account.controller.exceptionHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerImpl {

	@ExceptionHandler(ValidationFailedException.class)
	public ResponseEntity<?> handleException(final ValidationFailedException exception) {
		final AccountServiceApiError apiError = new AccountServiceApiError(exception.getStatus(), exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(final Exception exception) {
		log.error("Unhandled exception has occured: {}", exception.getMessage());
		final AccountServiceApiError apiError = new AccountServiceApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}
