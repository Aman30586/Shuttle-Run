package com.rides.exception;

public class RidesNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RidesNotFoundException(String message) {
		super(message);
	}
}
