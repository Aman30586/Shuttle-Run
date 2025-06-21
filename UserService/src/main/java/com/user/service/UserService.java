package com.user.service;

import java.util.List;

import com.user.entity.User;

public interface UserService {

	public User addUser(User user);
	public User updateUserPhoneNumber(long userId, long phoneNumber);
	public User updateUserDetails(long userId, String attribute, String details);
	public User displayUserById(long userId);
	public List<User> displayAllUsers();
	public void deleteUser(long userId);
	public User displayByUsername(String username);
}
