package com.neueda.test.atm.controller.errorHandler;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.neueda.test.atm.utils.Message;

public class ExceptionHandlerImplTest {

	private ExceptionHandlerImpl exceptionHandlerImpl = new ExceptionHandlerImpl();

	@Test
	public void testValidationFailedException() {
		final ValidationFailedException exception = new ValidationFailedException(HttpStatus.BAD_REQUEST,
				Message.INSUFFICIENT_ATM_CASH.message());
		final ResponseEntity<?> response = exceptionHandlerImpl.handleException(exception);
		assertEquals(response.getBody(),
				new ATMServiceApiError(HttpStatus.BAD_REQUEST, Message.INSUFFICIENT_ATM_CASH.message()));
	}
	
	@Test
	public void testHttpClientErrorException() {
		final HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid pin");
		final ResponseEntity<?> response = exceptionHandlerImpl.handleException(exception);
		assertEquals(response.getBody(), new ATMServiceApiError(HttpStatus.UNAUTHORIZED, "401 Invalid pin"));
	}

	@Test
	public void testUnhandledException() {
		final Exception exception = new Exception("Unhandled Exception");
		final ResponseEntity<?> response = exceptionHandlerImpl.handleException(exception);
		assertEquals(response.getBody(), new ATMServiceApiError(HttpStatus.BAD_REQUEST, "Unhandled Exception"));
	}

}
