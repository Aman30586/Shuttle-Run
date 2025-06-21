package com.rides.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException.FeignClientException;

@RestControllerAdvice
public class RideGlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(FeignClientException.class)
	public ResponseEntity<String> handleFeignClientException(FeignClientException ex, WebRequest request){
		String msg = ex.getMessage();
		int index = 0;
		for(int i = msg.length() - 1; i >= 0; i--) {
			if(msg.charAt(i) == '[') {
				index = i;
				break;
			}
		}
		msg = msg.substring(index);
		return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false), "Internal Server Error");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RideNotCancelledException.class)
	public ResponseEntity<ExceptionResponse> handleRideCancelException(RideNotCancelledException ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false), "Not Acceptable");
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	@ExceptionHandler(RidesNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleRidesNotFound(RidesNotFoundException ex, WebRequest request){
		ExceptionResponse response = new  ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false), "Not Found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
