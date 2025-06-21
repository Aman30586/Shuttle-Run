package com.security.entity;

import lombok.Data;

@Data
public class Driver {
	private long driverId;
	private String name;
	private String username;
	private String address;
	private String licenseNumber;
	private String email;
	private String phoneNumber;
}
