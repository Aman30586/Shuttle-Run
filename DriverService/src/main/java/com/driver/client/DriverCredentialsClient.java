package com.driver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.driver.entity.Credentials;

@FeignClient(name = "SecurityService")
public interface DriverCredentialsClient {

	@PostMapping("/auth/registerDriver")
	public void registerDriver(@RequestBody Credentials credentials);
	
	@DeleteMapping("/auth/deleteDriver/{username}")
	public void deleteDriverCredentials(@PathVariable String username);
	
	@PutMapping("/auth/updateDrivername/{username}/{email}")
	public void updateUsernameForDriver(@PathVariable String username, @PathVariable String email);
}
