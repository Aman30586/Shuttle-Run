package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.AuthRequest;
import com.security.entity.Credentials;
import com.security.entity.Customer;
import com.security.entity.Response;
import com.security.exception.UserDetailsAlreadyExistsException;
import com.security.repository.CredentialRepository;
import com.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
	
	@Autowired 
	private AuthService authService;
	
	@Autowired
	private CredentialRepository credentialRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//To Register the user in the system
	@PostMapping("/registerUser")
	public ResponseEntity<String> addNewUser(@RequestBody @Valid Customer customer) {
		if(authService.addUser(customer)) {
			return new ResponseEntity<>("Customer added Successfully!!", HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//To register the driver into the system
	@PostMapping("/registerDriver")
	public void registerDriver(@RequestBody Credentials credentials) {
		authService.registerDriver(credentials);
	}
	
	//To login into the application and getting the token to access rest of the authorized resources
	@PostMapping("/login")
	public ResponseEntity<Response> getToken(@RequestBody AuthRequest auth) throws UserDetailsAlreadyExistsException {
		Response response = null;
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
		if(authentication.isAuthenticated()) {
			Credentials credentials = credentialRepository.findByUsername(auth.getUsername());
			response = authService.generateToken(auth.getUsername(), credentials.getRole());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//To validate a token generated using credentials
	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestParam String token) {
		if(authService.validateToken(token)) {
		return new ResponseEntity<String>("Token is validated!!", HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<String>("Token isn't valid!!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/deleteUser/{email}")
	public void deleteUserCredentials(@PathVariable String email) {
		authService.deleteUser(email);
	}
	
	@DeleteMapping("/deleteDriver/{username}")
	public void deleteDriverCredentials(@PathVariable String username) {
		authService.deleteDriver(username);
	}
	
	@PutMapping("/updateUsername/{username}/{email}")
	public void updateUsernameForUser(@PathVariable String username, @PathVariable String email) {
		authService.updateUsernameForUser(username, email);
	}
	
	@PutMapping("/updateDrivername/{username}/{email}")
	public void updateUsernameForDriver(@PathVariable String username, @PathVariable String email) {
		log.info("inside update..!!");
		authService.updateUsernameForDriver(username, email);
	}
	
}
