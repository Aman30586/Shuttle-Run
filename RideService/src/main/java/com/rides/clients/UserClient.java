package com.rides.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rides.entity.User;

@FeignClient(name = "UserService")
public interface UserClient {

	@GetMapping("/user/display/{userId}")
	public ResponseEntity<User> displayUserById(@PathVariable long userId);
}
