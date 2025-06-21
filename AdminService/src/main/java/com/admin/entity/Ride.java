package com.admin.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Ride {

	private long rideId;
	private long driverId;
	private long userId;
	private String pickUpLocation;
	private String dropLocation;
	private String carType;
	private Date bookingDate;
	private Date endTime;
	private double fare;
	private String rideStatus;
}
