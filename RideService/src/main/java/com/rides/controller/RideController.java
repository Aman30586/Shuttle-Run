package com.rides.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rides.entity.DriverRideResponse;
import com.rides.entity.Ride;
import com.rides.entity.RidesResponse;
import com.rides.service.RideServiceImpl;

@RestController
@RequestMapping("/ride")
public class RideController {

	@Autowired
	RideServiceImpl rideService;
	
	//To book a ride using User ID
	@PostMapping("/bookride/{userId}")
	public ResponseEntity<RidesResponse> bookRide(@RequestBody Ride ride, @PathVariable long userId) {
		ride.setUserId(userId);
		RidesResponse response = rideService.bookRide(ride);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	//To display all the rides of a particular user using User ID
	@GetMapping("/displayRidesByUserId/{userId}")
	public ResponseEntity<List<RidesResponse>> fetchRidesByUserId(@PathVariable long userId) {
		List<RidesResponse> responses = rideService.fetchRidesByUserId(userId);
		return new ResponseEntity<List<RidesResponse>>(responses, HttpStatus.OK);
	}
	
	//To display all the rides of a particular driver using driver ID
	@GetMapping("/displayRidesByDriverId/{driverId}")
	public ResponseEntity<List<DriverRideResponse>> fetchRidesByDriverId(@PathVariable long driverId) {
		List<DriverRideResponse> responses = rideService.fetchRidesByDriverId(driverId);
		return new ResponseEntity<List<DriverRideResponse>>(responses, HttpStatus.OK);
	}
	
	//To display all the rides
	@GetMapping("/display")
	public ResponseEntity<List<Ride>> fetchAllRides() {
		List<Ride> rides = rideService.fetchAllRides();
		return new ResponseEntity<List<Ride>>(rides, HttpStatus.OK);
	}
	
	//To end the ride that is on-going
	@GetMapping("/endRide/{rideId}")
	public ResponseEntity<String> endRide(@PathVariable long rideId) {
		rideService.endRide(rideId);
		return new ResponseEntity<String>("Ride Ended!!", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/cancelRide/{rideId}")
	public ResponseEntity<String> cancelRide(@PathVariable long rideId) {
		rideService.cancelRide(rideId);
		return new ResponseEntity<>("Ride Cancelled!", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getFare/{pickup}/{drop}/{carType}")
	public ResponseEntity<String> getFare(@PathVariable String pickup, @PathVariable String drop, @PathVariable String carType){
		pickup = pickup.replaceAll("_", " ");
		drop = drop.replaceAll("_", " ");
		return new ResponseEntity<String>(rideService.getFare(pickup, drop, carType), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteRidesByUserId/{userId}")
	public void deleteRidesByUserId(@PathVariable long userId){
		rideService.deleteRidesByUserId(userId);
	}
	
	@DeleteMapping("/deleteRidesByDriverId/{driverId}")
	public void deleteRidesByDriverId(@PathVariable long driverId){
		rideService.deleteRidesByDriverId(driverId);
	}
}
