package com.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.admin.entity.Driver;

@FeignClient(name = "DriverService")
public interface DriverClient {

	@PostMapping("/driver/register")
	public void addDriver(@RequestBody Driver driver);
	
}