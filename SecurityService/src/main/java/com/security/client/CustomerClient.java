package com.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.security.entity.Customer;

@FeignClient(name = "UserService")
public interface CustomerClient {

	@PostMapping("/user/register")
	public Customer addUser(@RequestBody Customer customer);
	
	@GetMapping("/user/displayByUsername/{username}")
	public Customer displayByUsername(@PathVariable String username);
}
