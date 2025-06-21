package com.gateway.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AuthorizationGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllException(Exception ex) {
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), "Internal Server Error");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AuthorizationHeaderException.class)
	public ResponseEntity<ExceptionResponse> handleMissingAuthorizationHeader (AuthorizationHeaderException ex){
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), "Not Found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<ExceptionResponse> unauthorized (UnauthorizedAccessException ex){
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), "Unauthorized");
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(TokenIsNotValidException.class)
	public ResponseEntity<ExceptionResponse> tokenIsNotValid (TokenIsNotValidException ex){
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), "Not Acceptable");
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}
}
