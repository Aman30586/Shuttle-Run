package com.driver.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.driver.entity.Driver;

@Repository
public interface DriverRepository extends MongoRepository<Driver, Long>{
	
	public List<Driver> findByAvailableTrueAndCar_CarType(String carType);
	public Driver findByUsername(String username);
	public Driver findByLicenseNumber(String licenseNumber);
	public Driver findByPhoneNumber(String phoneNumber);
	public Driver findByEmail(String email);
	public Driver findByCar_CarNumber(String carNumber);
}
