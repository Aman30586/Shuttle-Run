package com.rides.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RidesResponse {

	private long rideId;
	private String pickUpLocation;
	private String dropLocation;
	private Driver driver;
	private String rideStatus;
	private double fare;
}
