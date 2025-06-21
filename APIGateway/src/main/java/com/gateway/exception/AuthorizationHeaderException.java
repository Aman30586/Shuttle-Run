package com.gateway.exception;

public class AuthorizationHeaderException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AuthorizationHeaderException(String message) {
		super(message);
	}
}
