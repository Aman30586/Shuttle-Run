package com.admin.entity;

import lombok.Data;


@Data
public class Driver {

	private long driverId;
	private String firstName;
	private String lastName;
	private String address;
	private String licenseNumber;
	private String email;
	private String password;
	private long phoneNumber;
	private boolean available;
	private Car car;
}
