package com.driver.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document("driver")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
	
	@Id
	private long driverId;
	@NotBlank(message = "Please provide Full Name")
	private String name;
	@NotBlank(message = "Please provide Username")
	private String username;
	@NotBlank(message = "Please provide Address")
	private String address;
	@Size(min = 12, max = 12, message = "Please provide valid License Number")
	private String licenseNumber;
	@Email(message = "Please provide email in the correct format")
	private String email;
	@Size(min = 10, max = 10, message = "Please provide a valid Phone Number")
	private String phoneNumber;
	private boolean available;
	private Car car;
}
