package com.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.user.client.RideClient;
import com.user.client.UserCredentialsClient;
import com.user.entity.User;
import com.user.exception.UserNotFoundException;
import com.user.repository.UserRepository;
import com.user.sequence.SequenceService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserDao {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserCredentialsClient credentialsClient;
	
	@Autowired
	RideClient rideClient;
	
	@Autowired
	SequenceService sequenceService;
	
	//To save the user details in the database
	public User addUser(User user) {
		log.info("User added successfully!!");
		user.setUserId(sequenceService.getNextSequence("Customer_Seq"));
		return userRepository.save(user);
	}
	
	//To update phone number and save the updated details in the database using User ID
	public User updateUserPhoneNumber(long userId, long phoneNumber) {
		System.out.println(userId);
		User user = userRepository.findById(userId).orElse(null);
		if(user == null) {
			log.error("User with id " + userId + " is Not Found!!");
			throw new UserNotFoundException("User with id " + userId + " is Not Found!!");
		}
		user.setUpdatedAt(new Date());
		user.setPhone(phoneNumber);
		return userRepository.save(user);
	}
	
	//To update user details and save the updated details in the database using User ID
	public User updateUserDetails(long userId, String attribute, String detailInfo) {
    	User user = userRepository.findById(userId).orElse(null);
    	if(user == null) {
    		log.error("User with id " + userId + " is Not Found!!");
    		throw new UserNotFoundException("User with id " + userId + " is Not Found!!");
    	}
    	switch(attribute) {
	    	case "Name":
	    		detailInfo = detailInfo.replaceAll("_", " ");
	    		user.setName(detailInfo);
	    		user.setUpdatedAt(new Date());
	    		break;
	    	case "Username":
	    		user.setUsername(detailInfo);
	    		user.setUpdatedAt(new Date());
	    		credentialsClient.updateUsernameForUser(detailInfo, user.getEmail());
	    		break;
	    	case "PhoneNumber":
	    		user.setPhone(Long.parseLong(detailInfo));
	    		user.setUpdatedAt(new Date());
	    		break;
    	}
    	return userRepository.save(user);
    }
	
	//To fetch the user details from the database using User ID
	public User displayUserById(long userId) {
		//User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " is Not Found!!"));
		User user = userRepository.findById(userId).orElse(null);
		if(user == null) {
    		log.error("User with id " + userId + " is Not Found!!");
    		throw new UserNotFoundException("User with id " + userId + " is Not Found!!");
    	}
		return user;
	}
	
	//To fetch all the users from the database 
	public List<User> displayAllUsers(){
		List<User> resList = userRepository.findAll();
		if(resList.isEmpty()) {
			throw new UserNotFoundException("No Users found!!");
		}
		return resList;
	}
	
	//To get the user details and delete it from the database using User ID
	public void deleteUser(long userId){
		//User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " is Not Found!!"));
		User user = userRepository.findById(userId).orElse(null);
		if(user == null) {
    		log.error("User with id " + userId + " is Not Found!!");
    		throw new UserNotFoundException("User with id " + userId + " is Not Found!!");
    	}
		rideClient.deleteRidesByUserId(userId);
		credentialsClient.deleteUserCredentials(user.getEmail());
		userRepository.delete(user);
	}

	public User displayByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user.get();
	}
}
