package com.rides.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRideResponse {
	
	private long rideId;
	private String pickUpLocation;
	private String dropLocation;
	private User user;
	private String rideStatus;
	private double fare;
}
