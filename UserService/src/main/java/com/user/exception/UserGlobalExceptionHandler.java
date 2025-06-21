package com.user.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class UserGlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllException(Exception ex) { //WebRequest request
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), "Internal Server Error");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex){//, WebRequest request
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), "Not Found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}	
}
