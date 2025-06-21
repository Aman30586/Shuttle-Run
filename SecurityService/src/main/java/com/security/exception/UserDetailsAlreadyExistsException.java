package com.security.exception;

public class UserDetailsAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDetailsAlreadyExistsException(String message) {
		super(message);
	}
}
