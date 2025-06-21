package com.rides.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

	@Id
	private long rideId;
	private long driverId;
	private long userId;
	private String pickUpLocation;
	private String dropLocation;
	private String carType;
	private Date bookingDate;
	private Date endTime;
	private double fare;
	
	transient private String rideStatus;
}
