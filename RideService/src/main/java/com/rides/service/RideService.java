package com.rides.service;

import java.util.List;

import com.rides.entity.DriverRideResponse;
import com.rides.entity.Ride;
import com.rides.entity.RidesResponse;

public interface RideService {

	public RidesResponse bookRide(Ride ride);
	public List<RidesResponse> fetchRidesByUserId(long userId);
	public List<DriverRideResponse> fetchRidesByDriverId(long driverId);
	public List<Ride> fetchAllRides();
	public void endRide(long rideId);
	public void cancelRide(long rideId);
	public String getFare(String pickup, String drop, String carType);
	public void deleteRidesByUserId(long userId);
	public void deleteRidesByDriverId(long driverId);
}
