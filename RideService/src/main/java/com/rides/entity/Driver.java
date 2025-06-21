package com.rides.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

	private long driverId;
	private String name;
	private long phoneNumber;
	private Car car;
}
