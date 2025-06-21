package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dao.UserDao;
import com.user.entity.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;

	@Override
	public User addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public User updateUserPhoneNumber(long userId, long phoneNumber) {
		return userDao.updateUserPhoneNumber(userId, phoneNumber);
	}
	
	public User updateUserDetails(long userId, String attribute, String detail) {
		return userDao.updateUserDetails(userId, attribute, detail);
	}

	@Override
	public User displayUserById(long userId) {
		return userDao.displayUserById(userId);
	}

	@Override
	public List<User> displayAllUsers() {
		return userDao.displayAllUsers();
	}

	@Override
	public void deleteUser(long userId) {
		userDao.deleteUser(userId);
	}

	@Override
	public User displayByUsername(String username) {
		return userDao.displayByUsername(username);
	}
}
