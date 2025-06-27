package com.zinkworks.atmmachine.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author Manish.Singh
 *
 */
@Getter
@Setter
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -5034492011577943064L;
	private HttpStatus status;

	public ValidationException(final HttpStatus status, final String message) {
		super(message);
		this.setStatus(status);
	}
}