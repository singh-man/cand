package com.neueda.test.account.controller.exceptionHandler;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.neueda.test.account.util.Message;

public class ExceptionHandlerImplTest {

	private ExceptionHandlerImpl exceptionHandlerImpl = new ExceptionHandlerImpl();

	@Test
	public void testValidationFailedException() {
		final ValidationFailedException exception = new ValidationFailedException(HttpStatus.BAD_REQUEST,
				Message.INCORRECT_PIN.message());
		final ResponseEntity<?> response = exceptionHandlerImpl.handleException(exception);
		assertEquals(response.getBody(),
				new AccountServiceApiError(HttpStatus.BAD_REQUEST, Message.INCORRECT_PIN.message()));
	}
	
	@Test
	public void testUnhandledException() {
		final Exception exception = new Exception("Unhandled Exception");
		final ResponseEntity<?> response = exceptionHandlerImpl.handleException(exception);
		assertEquals(response.getBody(),
				new AccountServiceApiError(HttpStatus.BAD_REQUEST, "Unhandled Exception"));
	}

}
