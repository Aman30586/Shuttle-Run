package com.security.entity;

import lombok.Data;

@Data
public class Response {

	private long id;
	private String name;
	private String username;
	private String email;   
    private String phone;
    private String role;
    private String token;
}
