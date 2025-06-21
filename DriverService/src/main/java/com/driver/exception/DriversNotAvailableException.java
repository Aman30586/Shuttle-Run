package com.driver.exception;

public class DriversNotAvailableException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DriversNotAvailableException(String message) {
		super(message);
	}
}
