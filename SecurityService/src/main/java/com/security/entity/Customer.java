package com.security.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Customer {
    private long userId;
	@NotEmpty(message = "Please provide User Name")
    private String username; 
	@NotBlank(message = "Please provide Full Name")
	private String name;
	@Email(message = "Please provide Email in correct format")
	@NotEmpty(message = "Please provide Email ID")
    private String email;
	@Size(min = 10, max = 10, message = "Please provide valid Phone Number")
    private String phone;  
    private String role; 
    @Size(min = 8, message = "Please provide Password")
    private String password;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
}

