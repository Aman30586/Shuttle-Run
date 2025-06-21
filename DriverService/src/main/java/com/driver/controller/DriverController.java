package com.driver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.driver.entity.Driver;
import com.driver.service.DriverServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/driver")
public class DriverController {

	@Autowired
	DriverServiceImpl driverService;
	
	//To add and register the driver
	@PostMapping("/register")
	public ResponseEntity<String> addDriver(@RequestBody @Valid Driver driver) {
//		try {			
			driverService.addDriver(driver);
			return new ResponseEntity<String> ("Driver registered Succesfully!!", HttpStatus.CREATED);
//		}
//		catch(Exception e) {
//			return new ResponseEntity<> (e.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}

	//To update driver details using Driver ID
	@PutMapping("/updateDetails/{driverId}/{attribute}/{detail}")
	public ResponseEntity<Driver> updateDriverDetails(@PathVariable long driverId,@PathVariable String attribute, @PathVariable String detail) {
//		try {			
			Driver driver = driverService.updateDriverDetails(driverId, attribute, detail);
			return new ResponseEntity<Driver>(driver, HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
	//To update availability of driver using driver ID
	@PutMapping("/updateAvailabilty/{driverId}/{flag}")
	public void updateAvailabilty(@PathVariable long driverId, @PathVariable boolean flag) {
		driverService.updateAvailabilty(driverId, flag);
	}

	//To display the driver using driver ID
	@GetMapping("/display/{driverId}")
	public ResponseEntity<Driver> fetchDriverById(@PathVariable long driverId) {
//		try {			
			Driver driver = driverService.fetchDriverById(driverId);
			return new ResponseEntity<Driver>(driver, HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}

	//To display the drivers by car type
	@GetMapping("/displayByCarType/{carType}")
	public List<Driver> fetchDriversByCarType(@PathVariable String carType) {
		return driverService.fetchDriversByCarType(carType);
	}

	//To display all the drivers
	@GetMapping("/display")
	public ResponseEntity<List<Driver>> fetchAllDrivers() {
//		try {			
			List<Driver> drivers = driverService.fetchAllDrivers();
			return new ResponseEntity<List<Driver>>(drivers, HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
	//To delete the driver using driver ID
	@DeleteMapping("/deleteDriver/{driverId}")
	public ResponseEntity<String> deleteDriver(@PathVariable long driverId) {
//		try {			
			driverService.deleteDriver(driverId);
			return new ResponseEntity<String>("Driver Deleted!!", HttpStatus.ACCEPTED);
//		}
//		catch(Exception e) {
//			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
	@GetMapping("/displayByUsername/{username}")
	public ResponseEntity<Driver> displayByUsername(@PathVariable String username){
		return new ResponseEntity<>(driverService.displayByUsername(username), HttpStatus.OK);
	}
}
