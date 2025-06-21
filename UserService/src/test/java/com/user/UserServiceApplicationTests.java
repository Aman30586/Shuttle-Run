package com.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.user.controller.UserController;
import com.user.dao.UserDao;
import com.user.entity.User;
import com.user.exception.ExceptionResponse;
import com.user.exception.UserGlobalExceptionHandler;
import com.user.exception.UserNotFoundException;
import com.user.sequence.Sequence;
import com.user.sequence.SequenceService;
import com.user.service.UserServiceImpl;

@SpringBootTest
class UserServiceApplicationTests {
	
	@Autowired
	UserServiceImpl serviceImpl;
	
	@Autowired
	SequenceService seqService;
	
	@Autowired
	UserController userController;	
	
	@Autowired
	UserGlobalExceptionHandler globalExceptionHandler;
	
	@Autowired
	UserDao userDao;
	
	User user = new User(1002, "Jaidev Mahant", "Name", "jaidev@example.com", 9876543210L, new Date(), new Date());
	User dummyUser = new User(1003, "Jaidev Mahant", "Name", "mahant@example.com", 9876543210L, new Date(), new Date());

	@BeforeEach
	public void addUser() {
		userController.addUser(dummyUser);
	}
	
	@AfterEach
	public void deleteUser() {
		userController.deleteUser(1003);
	}
	
	@Test
	public void testGetterAndSettersForUser() {
		User test = new User();
		test.setUserId(1004);
		test.setUsername("Shashank");
		test.setEmail("sha@gmail.com");
		test.setPhone(8765287421L);
		test.setCreatedAt(new Date());
		test.setUpdatedAt(new Date());
		
		ResponseEntity<User> response = userController.addUser(test);
		User testUser = response.getBody();
		assertEquals(testUser.getUserId(), test.getUserId());
		assertEquals(testUser.getUsername(), test.getUsername());
		assertEquals(testUser.getEmail(), test.getEmail());
		assertEquals(testUser.getPhone(), test.getPhone());
		assertEquals(testUser.getCreatedAt(), test.getCreatedAt());
		assertEquals(testUser.getUpdatedAt(), test.getUpdatedAt());
	}
	
	@Test
	public void testToStringForUser() {
		String testUser = user.toString();
		assertEquals(testUser, user.toString());
	}
	
	@Test
	public void testEqualsAndHashcodeForUser() {
		User user1 =  new User(1005, "Dev", "Name", "dev@example.com", 11111111111L, new Date(), new Date());
		User user2 =  user1;
		User user3 = new User();
		
		assertTrue(user1.equals(user2));
		assertTrue(user2.equals(user1));
		assertFalse(user1.equals(user3));
		assertFalse(user3.equals(user2));
		
		assertEquals(user1.hashCode(), user2.hashCode());
		assertNotEquals(user1.hashCode(), user3.hashCode());
	}
	
	
	@Test
	public void testConstructorForUser() {
		User test = new User();
		assertNotNull(test);
	}
	
	@Test
	public void testGetterAndSettersForSeq(){
		Sequence seq = new Sequence();
		seq.setSequence("Custom");
		seq.setSeqValue(11);
		
		assertEquals(seq.getSequence(), "Custom");
		assertEquals(seq.getSeqValue(), 11);
	}
	
	@Test
	public void testToSringForSeq() {
		Sequence seq = new Sequence("Custom", 11);
		String testSeq = seq.toString();
		assertEquals(testSeq, seq.toString());
	}
	
	@Test
	public void testEqualsAndHashCodeForSeq() {
		Sequence seq1 = new Sequence("Custom", 11);
		Sequence seq2 = new Sequence("Custom", 11);
		Sequence seq3 = new Sequence();
		
		assertTrue(seq1.equals(seq2));
		assertTrue(seq2.equals(seq1));
		assertFalse(seq1.equals(seq3));
		assertFalse(seq3.equals(seq2));
		
		assertEquals(seq1.hashCode(), seq2.hashCode());
		assertNotEquals(seq1.hashCode(), seq3.hashCode());
	}
	
	@Test
	public void testSeqService() {
		long value = seqService.getNextSequence("Test");
		assertEquals(value, 101);
	}
	
	@Test
	public void testToSringForExResponse() {
		LocalDate date = LocalDate.now();
		ExceptionResponse response = new ExceptionResponse(date, "msg", "msg");
		String testResponse = response.toString();
		assertEquals(testResponse, response.toString());
	} 
	
	@Test
	public void testConstructorForSeq() {
		Sequence testSeq = new Sequence();
		assertNotNull(testSeq);
	}
	
	@Test
	public void testGetterAndSettersForExResponse(){
		LocalDate date = LocalDate.now();
		ExceptionResponse response = new ExceptionResponse();
		response.setTimestamp(date);
		response.setMessage("msg");
		response.setHttpCodeMessage("msg");
		
		assertEquals(response.getTimestamp(), date);
		assertEquals(response.getMessage(), "msg");
		assertEquals(response.getHttpCodeMessage(), "msg");
	}
	
	@Test
	public void testEqualsAndHashCodeForExResponse() {
		LocalDate date = LocalDate.now();
		ExceptionResponse response1 = new ExceptionResponse(date, "msg", "msg");
		ExceptionResponse response2 = new ExceptionResponse(date, "msg", "msg");
		ExceptionResponse response3 = new ExceptionResponse();
		
		assertTrue(response1.equals(response2));
		assertTrue(response2.equals(response1));
		assertFalse(response1.equals(response3));
		assertFalse(response3.equals(response2));
		
		assertEquals(response1.hashCode(), response2.hashCode());
		assertNotEquals(response1.hashCode(), response3.hashCode());
		
	}
	
	@Test
	public void testConstructorForExResponse() {
		ExceptionResponse response = new ExceptionResponse();
		assertNotNull(response);
	}
	
	@Test
	public void testAddUser() {
		User testUser = user;
		ResponseEntity<User> response = userController.addUser(testUser);
		User newUser = response.getBody();
		assertEquals(testUser, newUser);
	}
	
	@Test
	public void testUpdateUserPhoneNumber() throws UserNotFoundException {
		userDao.addUser(dummyUser);
		ResponseEntity<User> response = userController.updateUserPhoneNumber(1003, 12345678901L);
		User testUser = response.getBody();
		assertEquals(testUser.getUserId(), dummyUser.getUserId());
	}
	
	@Test
	public void testUpdateUserPhoneNumberException() throws UserNotFoundException {
		Exception ex = assertThrows(UserNotFoundException.class, () -> {
			userDao.updateUserPhoneNumber(1010, 1234567890l);
	    });
		
		String actual = "User with id " + 1010 + " is Not Found!!";
		String expected = ex.getMessage();
		assertEquals(actual, expected);
	}
	
	@Test
	public void testUpdateUserName() throws UserNotFoundException {
		ResponseEntity<User> response = userController.updateUserDetails(1003, "Name", "Jaidev");
		User testUser = response.getBody();
		assertEquals(testUser.getUserId(), dummyUser.getUserId());
	}
	
	@Test
	public void testUpdateUserEmail() throws UserNotFoundException {
		ResponseEntity<User> response = userController.updateUserDetails(1003, "Email", "mmm@example.com");
		User testUser = response.getBody();
		assertEquals(testUser.getUserId(), dummyUser.getUserId());
	}
	
	@Test
	public void testUpdateDetailException() throws UserNotFoundException {
		Exception ex = assertThrows(UserNotFoundException.class, () -> {
			userDao.updateUserDetails(1010, "Name", "Gas");
	    });
		
		String actual = "User with id " + 1010 + " is Not Found!!";
		String expected = ex.getMessage();
		assertEquals(actual, expected);
	}
	
	@Test
	public void testDisplayUserById() throws UserNotFoundException {
		ResponseEntity<User> response = userController.displayUserById(102);
		User testUser = response.getBody();
		assertEquals(testUser.getUserId(), 102);
	}
	
	@Test
	public void testDisplayUserByIdlException() throws UserNotFoundException {
		Exception ex = assertThrows(UserNotFoundException.class, () -> {
			userDao.displayUserById(1010);
	    });
		
		String actual = "User with id " + 1010 + " is Not Found!!";
		String expected = ex.getMessage();
		assertEquals(actual, expected);
	}
	
	@Test
	public void testDisplayAllUsers() {
		ResponseEntity<List<User>> response = userController.displayAllUsers(); 
		List<User> userList = response.getBody();
		List<User> users = serviceImpl.displayAllUsers();
		assertEquals(userList.size(), users.size());
	}
	
	@Test
	public void testDeleteUser() throws UserNotFoundException {
		ResponseEntity<String> response = userController.deleteUser(1002);
		String testUser1 = response.getBody();
		assertEquals(testUser1, "User deleted!!");
	}
	
	@Test
	public void testDeleteUserException() throws UserNotFoundException {
		Exception ex = assertThrows(UserNotFoundException.class, () -> {
			userDao.deleteUser(1010);
	    });
		
		String actual = "User with id " + 1010 + " is Not Found!!";
		String expected = ex.getMessage();
		assertEquals(actual, expected);
	}
	
	@Test
	public void testGlobalExceptionHandler() throws Exception {
		Exception ex = assertThrows(Exception.class, () -> {
			throw new Exception("Global Exception Test");
		});
		ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handleAllException(ex);
		
		String actual = "Global Exception Test";
		ExceptionResponse expected = response.getBody();
		assertEquals(actual, expected.getMessage());
	}
	
	@Test
	public void testUserExceptionHandler() throws UserNotFoundException {
		UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> {
			throw new UserNotFoundException("User not found Exception Test");
		});
		ResponseEntity<ExceptionResponse> response = globalExceptionHandler.handleUserNotFoundException(ex);
		
		String actual = "User not found Exception Test";
		ExceptionResponse expected = response.getBody();
		assertEquals(actual, expected.getMessage());
	}
	
}
