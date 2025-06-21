package com.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.client.DriverClient;
import com.admin.entity.Driver;

@RestController
public class AdminController {
	
	@Autowired
	DriverClient driverClient;
	
	@PostMapping("/addDriver")
	public String addDriver(@RequestBody Driver driver) {
		driverClient.addDriver(driver);
		return "Driver added successfully!!";
	} 
}
