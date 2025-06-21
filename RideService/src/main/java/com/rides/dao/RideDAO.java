package com.rides.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rides.clients.DriverClient;
import com.rides.clients.UserClient;
import com.rides.entity.Driver;
import com.rides.entity.DriverRideResponse;
import com.rides.entity.Ride;
import com.rides.entity.RidesResponse;
import com.rides.entity.User;
import com.rides.exception.RideNotCancelledException;
import com.rides.exception.RidesNotFoundException;
import com.rides.repository.RideRepository;
import com.rides.sequence.SequenceService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RideDAO {

	@Autowired
	RideRepository rideRepository;
	
	@Autowired
	DriverClient driverClient;
	
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
	UserClient userClient;
	
	//Getting the ride and driver details and 
	public RidesResponse bookRide(Ride ride, Driver driver) {
		ride.setRideId(sequenceService.getNextSequence("Rides_Seq"));
		log.info("Ride booked successfully with ID " + ride.getRideId());
		rideRepository.save(ride);
		RidesResponse rideResponse = new RidesResponse(ride.getRideId(), ride.getPickUpLocation(), ride.getDropLocation(), driver, ride.getRideStatus(), ride.getFare());
		return rideResponse;
	}
	
	//To fetch all the ride details of a particular user using User ID
	public List<RidesResponse> fetchRidesByUserId(long userId) {
		List<Ride> rideList = rideRepository.findByUserId(userId);
		if(rideList.isEmpty()) {
			log.error("No rides found for Driver with user Id " + userId);
			throw new RidesNotFoundException("No rides found for Driver with user Id " + userId);
		}
		List<RidesResponse> responseList = new ArrayList<>();
		for(Ride ride : rideList) {
			ResponseEntity<Driver> response = driverClient.fetchDriverById(ride.getDriverId());
			Driver driver = response.getBody();
			RidesResponse rideResponse = new RidesResponse(ride.getRideId(), ride.getPickUpLocation(), ride.getDropLocation(), driver, ride.getRideStatus(), ride.getFare());
			responseList.add(rideResponse);
		}
		return responseList;
	}
	
	//To fetch all the ride details of a particular driver using Driver ID
	public List<DriverRideResponse> fetchRidesByDriverId(long driverId) {
		List<Ride> rideList = rideRepository.findByDriverId(driverId);
		if(rideList.isEmpty()) {
			log.error("No rides found for Driver with driver Id " + driverId);
			throw new RidesNotFoundException("No rides found for Driver with driver Id " + driverId);
		}
		List<DriverRideResponse> responseList = new ArrayList<>();
		for(Ride ride : rideList) {
			ResponseEntity<User> response = userClient.displayUserById(ride.getUserId());
			User user = response.getBody();
			DriverRideResponse driverRideResponse = new DriverRideResponse(ride.getRideId() ,ride.getPickUpLocation(), ride.getDropLocation(), user, ride.getRideStatus(), ride.getFare());
			System.out.println(driverRideResponse);
			responseList.add(driverRideResponse);
		}
		return responseList;
	}

	//To fetch all the rides from the database
	public List<Ride> fetchAllRides() {
		List<Ride> rideList = rideRepository.findAll();
		if(rideList.isEmpty()) {
			log.error("No rides found!!");
			throw new RidesNotFoundException("No rides found!!");
		}
		return rideList;
	}

	//To end ride using the ride ID and change the status of ride
	public void endRide(long rideId) {
		Ride ride = rideRepository.findById(rideId).orElseThrow(() -> {
			log.error(("No ride found for ride with ID " + rideId));
			return new RidesNotFoundException("No ride found for ride with ID " + rideId);
		});
		driverClient.updateAvailabilty(ride.getDriverId(), true);
		ride.setRideStatus("Completed");
		ride.setEndTime(new  Date());
		rideRepository.save(ride);
	}
	
	public void cancelRide(long rideId) {
		Ride ride = rideRepository.findById(rideId).orElseThrow(() -> {
			log.error(("No ride found for ride with ID " + rideId));
			return new RidesNotFoundException("No ride found for ride with ID " + rideId);
		});
		if(ride.getRideStatus().equals("Completed")) {
			throw new RideNotCancelledException("[Ride is already completed!]");
		}
		driverClient.updateAvailabilty(ride.getDriverId(), true);
		ride.setRideStatus("Cancelled");
		rideRepository.save(ride);
	}

	public void deleteRidesByUserId(long userId) {
		List<Ride> userRides = rideRepository.findByUserId(userId);
		for(Ride ride : userRides) {
			rideRepository.delete(ride);
		}
	}

	public void deleteRidesByDriverId(long driverId) {
		List<Ride> driverRides = rideRepository.findByUserId(driverId);
		for(Ride ride : driverRides) {
			rideRepository.delete(ride);
		}
	}
}
