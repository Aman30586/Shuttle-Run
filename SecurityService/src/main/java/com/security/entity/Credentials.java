package com.security.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document("credentials")
@Data
@AllArgsConstructor
public class Credentials {
	@Id
	private String email;
	private String username;
	private String password;
	private String role;
}
