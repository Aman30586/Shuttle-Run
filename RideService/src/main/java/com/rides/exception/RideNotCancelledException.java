package com.rides.exception;

public class RideNotCancelledException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RideNotCancelledException(String message) {
		super(message);
	}

}
