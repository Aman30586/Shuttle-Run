package com.rides.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.rides.entity.Driver;

@FeignClient(name = "DriverService")
public interface DriverClient {

	@GetMapping("/driver/displayByCarType/{carType}")
	public List<Driver> fetchDriversByCarType(@PathVariable String carType);
	
	@GetMapping("/driver/display/{driverId}")
	public ResponseEntity<Driver> fetchDriverById(@PathVariable long driverId);

	@PutMapping("/driver/updateAvailabilty/{driverId}/{flag}")
	public void updateAvailabilty(@PathVariable long driverId, @PathVariable boolean flag);
}
