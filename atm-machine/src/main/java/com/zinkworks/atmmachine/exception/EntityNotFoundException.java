package com.zinkworks.atmmachine.exception;

/**
 * 
 * @author Manish.Singh
 *
 */
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5034492011577943064L;

	public EntityNotFoundException(final String message) {
		super(message);
	}

}