package com.user.exception;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

	private LocalDate timestamp;
	private String message;
//	private String details;
	private String httpCodeMessage;
}
