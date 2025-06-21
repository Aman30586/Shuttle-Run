package com.driver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credentials {
	
	private String email;
	private String username;
	private String password;
}
