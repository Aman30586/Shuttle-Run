package com.security.exception;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

	private LocalDate timestamp;
	private String message;
	private String details;
	private String httpCodeMessage;
}
