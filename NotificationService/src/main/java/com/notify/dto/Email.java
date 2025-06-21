package com.notify.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Email implements Serializable{

	private String to;
	private String subject;
	private String body;
}
