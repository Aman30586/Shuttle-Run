package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	//Adding user details to the database
	@PostMapping("/register")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User userResponse = userService.addUser(user);
		return new ResponseEntity<User>(userResponse, HttpStatus.ACCEPTED);
	}
	
	//To update user's Phone Number in the database
	@PutMapping("/updatePhoneNumber/{userId}/{phoneNumber}")
	public ResponseEntity<User> updateUserPhoneNumber(@PathVariable long userId, @PathVariable long phoneNumber){
		User user = userService.updateUserPhoneNumber(userId, phoneNumber);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	//To update details like User name, Email, etc  in the database
	@PutMapping("/updateDetails/{userId}/{attribute}/{detail}")
	public ResponseEntity<User> updateUserDetails(@PathVariable long userId, @PathVariable String attribute, @PathVariable String detail) {
		User user = userService.updateUserDetails(userId, attribute, detail);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	//To display the user with the help of User ID
	@GetMapping("/display/{userId}")
	public ResponseEntity<User> displayUserById(@PathVariable long userId) {
		User user = userService.displayUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/displayByUsername/{username}")
	public User displayByUsername(@PathVariable String username) {
		return userService.displayByUsername(username);
	}

	
	//To display all the users
	@GetMapping("/display")
	public ResponseEntity<List<User>> displayAllUsers(){
		List<User> users = userService.displayAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	//To delete the user with the help of User ID
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>("User deleted!!", HttpStatus.ACCEPTED);
	}
}
