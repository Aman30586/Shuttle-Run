package com.driver.service;

import java.util.List;

import com.driver.entity.Driver;

public interface DriverService {

	public void addDriver(Driver driver);
	public Driver updateDriverDetails(long driverId, String attribute, String detail);
	public Driver fetchDriverById(long driverId);
	public List<Driver> fetchDriversByCarType(String carType);
	public List<Driver> fetchAllDrivers();
	public void deleteDriver(long driverId);
	public void updateAvailabilty(long driverId, boolean flag);
	public Driver displayByUsername(String username);
}
