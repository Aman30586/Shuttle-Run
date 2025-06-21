package com.driver.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.driver.client.DriverCredentialsClient;
import com.driver.client.RideClient;
import com.driver.entity.Credentials;
import com.driver.entity.Driver;
import com.driver.exception.DriverNotFoundException;
import com.driver.exception.DriversNotAvailableException;
import com.driver.repository.DriverRepository;
import com.driver.sequence.SequenceService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DriverDAO {

	@Autowired
	DriverRepository driverRepository;
	
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
	DriverCredentialsClient credentialsClient;
	
	@Autowired
	RideClient rideClient;
	
	//To add driver details to the database
	public void addDriver(Driver driver) {
		driver.setAvailable(true);
		driver.setDriverId(sequenceService.getNextSequence("Driver_Seq"));
		Credentials credentials = new Credentials(driver.getEmail(), driver.getUsername(), driver.getPhoneNumber());
		credentialsClient.registerDriver(credentials);
		log.info("Driver added successfully!!");
		driverRepository.save(driver);
	}
	
	//To update driver details like Name, Address, etc in database using driver ID
	public Driver updateDriverDetails(long driverId, String attribute, String detail) {
		Driver driver = driverRepository.findById(driverId).orElseThrow(() -> {
			log.error("Driver Not Found!!");
			return new DriverNotFoundException("Driver Not Found!!");
		});
		switch(attribute) {
		case "Name":
			driver.setName(detail);
			break;
		case "Username":
			credentialsClient.updateUsernameForDriver(detail, driver.getEmail());
			driver.setUsername(detail);
			break;
		case "Address":
			driver.setAddress(detail);
			break;
		}
		return driverRepository.save(driver);
	}
	
	//To fetch driver details using driver ID from the database
	public Driver fetchDriverById(long driverId) {
		Driver driver = driverRepository.findById(driverId).orElseThrow(() -> {
			log.error("Driver Not Found!!");
			return new DriverNotFoundException("Driver Not Found!!");
		});
		return driver;	
	}
	
	//To fetch the list of drivers by carType
	public List<Driver> fetchDriversByCarType(String carType) {
		List<Driver> myDrivers = driverRepository.findByAvailableTrueAndCar_CarType(carType);
		if(myDrivers.isEmpty()) {
			log.error("Drivers are not Available for " + carType + " cars");
			throw new DriversNotAvailableException("Drivers are not Available for " + carType + " cars");
		}
		return myDrivers;
	}
	
	//To fetch all the drivers in the database
	public List<Driver> fetchAllDrivers(){
		List<Driver> driverList = driverRepository.findAll();
		if(driverList.isEmpty()) {
			log.error("Driver Not Found!!");
			throw new DriverNotFoundException("Driver Not Found!!");
		}
		return driverList;
	}
	
	//To delete driver from the database using Driver ID
	public void deleteDriver(long driverId) {
		System.out.println(driverId);
		Driver driver = driverRepository.findById(driverId).orElseThrow(() -> {
			log.error("Driver Not Found!!");
			return new DriverNotFoundException("Driver Not Found!!");
		});
		rideClient.deleteRidesByDriverId(driverId);
		credentialsClient.deleteDriverCredentials(driver.getUsername());
		driverRepository.delete(driver);
	}

	//To update driver's availability in the database
	public void updateAvailabilty(long driverId, boolean flag) {
		Driver driver = driverRepository.findById(driverId).orElseThrow(() -> {
			log.error("Driver Not Found!!");
			return new DriverNotFoundException("Driver Not Found!!");
		});
		driver.setAvailable(flag);
		driverRepository.save(driver);
	}
	
	public Driver displayByUsername(String username) {
		return driverRepository.findByUsername(username);
	}
}
