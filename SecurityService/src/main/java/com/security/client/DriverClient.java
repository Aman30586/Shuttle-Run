package com.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.security.entity.Driver;

@FeignClient(name = "DriverService")
public interface DriverClient {

	@GetMapping("/driver/displayByUsername/{username}")
	public ResponseEntity<Driver> displayByUsername(@PathVariable String username);
}
