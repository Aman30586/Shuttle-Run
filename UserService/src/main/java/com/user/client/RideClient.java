package com.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RideService")
public interface RideClient {

	@DeleteMapping("/ride/deleteRidesByUserId/{userId}")
	public void deleteRidesByUserId(@PathVariable long userId);
}
