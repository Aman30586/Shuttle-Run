package com.driver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RideService")
public interface RideClient {

	@DeleteMapping("/ride/deleteRidesByDriverId/{driverId}")
	public void deleteRidesByDriverId(@PathVariable long driverId);
}
