package com.security.exception;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AuthenticationGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false), "Internal Server Error");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserDetailsAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleUserDetailsAlreadyExists(UserDetailsAlreadyExistsException ex, WebRequest request){
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false), "Conflict");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
		BindingResult result = ex.getBindingResult();
		List<String> errors = result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
		ExceptionResponse response = new ExceptionResponse(LocalDate.now(), errors.toString(), request.getDescription(false), "Bad Request");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
