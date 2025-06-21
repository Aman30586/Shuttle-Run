package com.gateway.exception;

public class TokenIsNotValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenIsNotValidException(String message) {
		super(message);
	}
}
