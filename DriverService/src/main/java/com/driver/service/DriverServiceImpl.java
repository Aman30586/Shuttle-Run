package com.driver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.dao.DriverDAO;
import com.driver.entity.Driver;
import com.driver.exception.DetailsAlreadyExistsException;
import com.driver.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService{
	
	@Autowired
	DriverDAO driverDAO;
	
	@Autowired
	DriverRepository driverRepository;

	@Override
	public void addDriver(Driver driver) {
		List<String> message = new ArrayList<>();
		Driver fetchedDriver = driverRepository.findByLicenseNumber(driver.getLicenseNumber());
		if(fetchedDriver != null) {
			message.add("License Number already exists");
			fetchedDriver = null;
		}
		fetchedDriver = driverRepository.findByPhoneNumber(driver.getPhoneNumber());
		if(fetchedDriver != null) {
			message.add("Phone Number already exists");
			fetchedDriver = null;
		}
		fetchedDriver = driverRepository.findByEmail(driver.getEmail());
		if(fetchedDriver != null) {
			message.add("Email Id already exists");
			fetchedDriver = null;
		}
		fetchedDriver = driverRepository.findByCar_CarNumber(driver.getCar().getCarNumber());
		if(fetchedDriver != null) {
			message.add("Car with number " + driver.getCar().getCarNumber() + " already exists");
			fetchedDriver = null;
		}
		if(!message.isEmpty()) {
			throw new DetailsAlreadyExistsException(message.toString());
		}
		driverDAO.addDriver(driver);
	}

	@Override
	public Driver updateDriverDetails(long driverId, String attribute, String detail) {
		return driverDAO.updateDriverDetails(driverId, attribute, detail);
	}

	@Override
	public Driver fetchDriverById(long driverId) {
		return driverDAO.fetchDriverById(driverId);
	}

	@Override
	public List<Driver> fetchDriversByCarType(String carType) {
		return driverDAO.fetchDriversByCarType(carType);
	}

	@Override
	public List<Driver> fetchAllDrivers() {
		return driverDAO.fetchAllDrivers();
	}

	@Override
	public void deleteDriver(long driverId) {
		driverDAO.deleteDriver(driverId);
	}

	@Override
	public void updateAvailabilty(long driverId, boolean flag) {
		driverDAO.updateAvailabilty(driverId, flag);
	}

	@Override
	public Driver displayByUsername(String username) {
		return driverDAO.displayByUsername(username);
	}
	
}
