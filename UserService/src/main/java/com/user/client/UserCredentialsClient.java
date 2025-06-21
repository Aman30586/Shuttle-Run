package com.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "SecurityService")
public interface UserCredentialsClient {
	
	@DeleteMapping("/auth/deleteUser/{email}")
	public void deleteUserCredentials(@PathVariable String email);
	
	@PutMapping("/auth/updateUsername/{username}/{email}")
	public void updateUsernameForUser(@PathVariable String username, @PathVariable String email);
}
