package com.security.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.client.CustomerClient;
import com.security.client.DriverClient;
import com.security.entity.Credentials;
import com.security.entity.Customer;
import com.security.entity.Driver;
import com.security.entity.Response;
import com.security.exception.UserDetailsAlreadyExistsException;
import com.security.repository.CredentialRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

	@Autowired
	private CredentialRepository credentialRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	CustomerClient customerClient;
	
	@Autowired
	DriverClient driverClient;
	
	/**
	 * To check and add the customer details into the database
	 * accepts customer object as argument
	 * return a boolean value on the basis of which a response to user is returned to Customer
	 * */
	public boolean addUser(Customer customer) {
		List<String> message = new ArrayList<>();
		Credentials checkCredentials = credentialRepository.findById(customer.getEmail()).orElse(null);
		if(checkCredentials != null) {
			message.add("Email id already exists!!");		
		}
		checkCredentials = credentialRepository.findByUsername(customer.getUsername());
		if(checkCredentials != null) {
			message.add("User name already exists!!");
		}
		if(!message.isEmpty()) {
			throw new UserDetailsAlreadyExistsException(message.toString());
		}
		customer.setRole("USER");
		customer.setCreatedAt(new Date());
		customer.setUpdatedAt(new Date());
		customerClient.addUser(customer);
		Credentials credentials = new Credentials(customer.getEmail(), customer.getUsername(), passwordEncoder.encode(customer.getPassword()), customer.getRole());
		log.info("Customer credentials added successfully!");
		credentialRepository.save(credentials);
		return true;	
	}
	
	//To check and add driver details into the database
	public void registerDriver(Credentials credentials) {
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		credentials.setRole("DRIVER");
		log.info("Driver credentials added successfully!");
		credentialRepository.save(credentials);
	}
	
	//To generate token on the basis of User name and role
	public Response generateToken(String userName, String role) {
		log.info("Token generated successfully!");
		String token = jwtService.generateToken(userName, role);
		Response response = new Response();
		if(role.equals("USER")) {
			Customer customer = customerClient.displayByUsername(userName);
			response.setId(customer.getUserId());
			response.setName(customer.getName());
			response.setUsername(customer.getUsername());
			response.setEmail(customer.getEmail());
			response.setPhone(customer.getPhone());
			response.setRole(role);
			response.setToken(token);
		}
		else if(role.equals("DRIVER")) {
			Driver driver = driverClient.displayByUsername(userName).getBody();
			response.setId(driver.getDriverId());
			response.setName(driver.getName());
			response.setUsername(driver.getUsername());
			response.setEmail(driver.getEmail());
			response.setPhone(driver.getPhoneNumber());
			response.setRole(role);
			response.setToken(token);
		}
		else if(role.equals("ADMIN")) {
			response.setName(userName);
			response.setRole(role);
			response.setToken(token);
		}
		return response;
	}
	
	//To validate the token with the help of JWTS parser
	public boolean validateToken(String token) {
		return jwtService.validateToken(token);
	}

	public void deleteUser(String email) {
		log.info("User Credentials deleted!");
		credentialRepository.deleteById(email);
	}

	public void deleteDriver(String username) {
		log.info("Driver Credentials deleted!");
		Credentials credentials = credentialRepository.findByUsername(username);
		credentialRepository.delete(credentials);
	}

	public void updateUsernameForUser(String username, String email) {
		log.info("Updating user's username");
		Credentials credentials = credentialRepository.findByEmail(email);
		credentials.setUsername(username);
		credentialRepository.save(credentials);
	}

	public void updateUsernameForDriver(String username, String email) {
		log.info("Updating driver's username");
		Credentials credentials = credentialRepository.findByEmail(email);
		credentials.setUsername(username);
		credentialRepository.save(credentials);
	}

	
}
