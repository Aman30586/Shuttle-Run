package com.rides.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rides.clients.DriverClient;
import com.rides.dao.RideDAO;
import com.rides.entity.Driver;
import com.rides.entity.DriverRideResponse;
import com.rides.entity.Ride;
import com.rides.entity.RidesResponse;

@Service
public class RideServiceImpl implements RideService{
	
	@Autowired
	DriverClient driverClient;
	
	@Autowired
	RideDAO rideDAO;
	
	Map<String, Integer> locations = new HashMap<>();

	@Override
	public RidesResponse bookRide(Ride ride) {
		List<Driver> driverList = driverClient.fetchDriversByCarType(ride.getCarType());
		driverClient.updateAvailabilty(driverList.get(0).getDriverId(), false);
		Driver driver = driverList.get(0);
		ride.setDriverId(driver.getDriverId());
		ride.setBookingDate(new Date());
		ride.setRideStatus("Booked");
		ride.setFare(calculateFare(ride.getPickUpLocation(), ride.getDropLocation(), ride.getCarType()));
		return rideDAO.bookRide(ride, driver);
	}

	@Override
	public List<RidesResponse> fetchRidesByUserId(long userId) {
		return rideDAO.fetchRidesByUserId(userId);
	}

	@Override
	public List<DriverRideResponse> fetchRidesByDriverId(long driverId) {
		return rideDAO.fetchRidesByDriverId(driverId);
	}
	
	@Override
	public List<Ride> fetchAllRides() {
		return rideDAO.fetchAllRides();
	}

	@Override
	public void endRide(long rideId) {
		rideDAO.endRide(rideId);
	}
		
	@Override
	public void deleteRidesByUserId(long userId) {
		rideDAO.deleteRidesByUserId(userId);
	}
	
	@Override
	public void deleteRidesByDriverId(long driverId) {
		rideDAO.deleteRidesByDriverId(driverId);
	}
	
	private double calculateFare(String pickUpLocation, String dropLocation, String carType) {
		double fare = 0.0;
		locations.put("Ecospace", 4);
		locations.put("Bellandur", 6);
		locations.put("Green Glen Layout", 8);
		locations.put("Sarajapur Rd", 10);
		locations.put("HSR Layout", 12);
		locations.put("Kundlahalli", 16);
		locations.put("Kormangla", 18);
		locations.put("MG Road", 20);
		locations.put("Indira Nagar", 22);
		locations.put("KIA", 25);
		
		if(carType.equals("Micro")) {
			fare = 25 + Math.abs(locations.get(pickUpLocation) - locations.get(dropLocation))*5;			
		}
		else if(carType.equals("Mini")) {
			fare = 25 + Math.abs(locations.get(pickUpLocation) - locations.get(dropLocation))*7;
		}
		else if(carType.equals("Sedan")) {
			fare = 25 + Math.abs(locations.get(pickUpLocation) - locations.get(dropLocation))*9;
		}
		return fare;
	}

	@Override
	public void cancelRide(long rideId) {
		rideDAO.cancelRide(rideId);
	}

	@Override
	public String getFare(String pickup, String drop, String carType) {
		return String.valueOf(calculateFare(pickup, drop, carType));
	}

}
