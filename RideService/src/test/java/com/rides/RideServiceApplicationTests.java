package com.rides;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.rides.controller.RideController;
import com.rides.entity.Car;
import com.rides.entity.Driver;
import com.rides.entity.DriverRideResponse;
import com.rides.entity.Ride;
import com.rides.entity.RidesResponse;
import com.rides.entity.User;
import com.rides.exception.ExceptionResponse;
import com.rides.exception.RidesNotFoundException;
import com.rides.sequence.Sequence;
import com.rides.sequence.SequenceService;
import com.rides.service.RideServiceImpl;

@SpringBootTest
class RideServiceApplicationTests {
	
	@Autowired
	RideController rideController;
	
	@Autowired
	RideServiceImpl serviceImpl;

	@Autowired
	SequenceService seqService;
	
	Car car1 = new Car("UH09KN2011", "Tata Indigo", "Micro");
	Driver driver1 = new Driver(1001, "Kishan", 1235678909, car1);
	User user1 = new User("Aman", 1235656567);
	Ride ride1 = new Ride(10, 1001, 1001, "Ecospace", "Bellandur", "Micro", new Date(), new Date(), 52, "Booked");
	
	@Test
	public void testRidesByDriverId() {
		ResponseEntity<List<DriverRideResponse>> responseEntity = rideController.fetchRidesByDriverId(103);
		List<DriverRideResponse> rides = responseEntity.getBody();
		List<DriverRideResponse> rideList = serviceImpl.fetchRidesByDriverId(103);
		assertEquals(rides.size(), rideList.size());
	}
	
	@Test
	public void testRidesByDriverIdException() {
		Exception ex = assertThrows(RidesNotFoundException.class, () -> {
			rideController.fetchRidesByDriverId(1001);
		});
		
		String exp = "No rides found for Driver with driver Id 1001";
		String actual = ex.getMessage();
		assertEquals(exp, actual);
	}
	
	@Test
	public void testRidesByUserId() {
		ResponseEntity<List<RidesResponse>> responseEntity = rideController.fetchRidesByUserId(106);
		List<RidesResponse> rides = responseEntity.getBody();
		List<RidesResponse> rideList = serviceImpl.fetchRidesByUserId(106);
		assertEquals(rides.size(), rideList.size());
	}

	@Test
	public void testEqualsAndHashCodeForDriverResponse() {
		DriverRideResponse ride1 = new DriverRideResponse(1001, "Ecospace", "Bellandur", user1, "Booked", 52);
		DriverRideResponse ride2 = new DriverRideResponse(1001, "Ecospace", "Bellandur", user1, "Booked", 52);
		DriverRideResponse ride3 = new DriverRideResponse();
		
		assertTrue(ride1.equals(ride2));
		assertFalse(ride1.equals(ride3));
		
		assertEquals(ride1.hashCode(), ride2.hashCode());
		assertNotEquals(ride1.hashCode(), ride3.hashCode());
	}
	
	@Test
	public void testSettersAndGetterForDriverResponse() {
		DriverRideResponse response = new DriverRideResponse();
		response.setPickUpLocation("Ecospace");
		response.setDropLocation("Bellandur");
		response.setUser(user1);
		response.setRideStatus("Booked");
		response.setFare(52);
		
		assertEquals(response.getUser(), user1);
		assertEquals(response.getDropLocation(), "Bellandur");
		assertEquals(response.getPickUpLocation(), "Ecospace");
		assertEquals(response.getFare(), 52);
		assertEquals(response.getRideStatus(), "Booked");
	}
	
	@Test
	public void testConstructorForDriverResponse() {
		DriverRideResponse response = new DriverRideResponse();
		assertNotNull(response);
	}
	
	@Test
	public void testToStringForDriverResponse() {
		DriverRideResponse response = new DriverRideResponse(1001, "Ecospace", "Bellandur", user1, "Booked", 52);
		String test = response.toString();
		assertEquals(test, response.toString());
	}
	
	@Test
	public void testEqualsAndHashCodeForUserResponse() {
		RidesResponse ride1 = new RidesResponse(1001, "Ecospace", "Bellandur", driver1, "Booked", 52);
		RidesResponse ride2 = new RidesResponse(1001, "Ecospace", "Bellandur", driver1, "Booked", 52);
		RidesResponse ride3 = new RidesResponse();
		
		assertTrue(ride1.equals(ride2));
		assertFalse(ride1.equals(ride3));
		
		assertEquals(ride1.hashCode(), ride2.hashCode());
		assertNotEquals(ride1.hashCode(), ride3.hashCode());
	}
	
	@Test
	public void testSettersAndGetterForUserResponse() {
		RidesResponse response = new RidesResponse();
		response.setPickUpLocation("Ecospace");
		response.setDropLocation("Bellandur");
		response.setDriver(driver1);
		response.setRideStatus("Booked");
		response.setFare(52);
		
		assertEquals(response.getDriver(), driver1);
		assertEquals(response.getDropLocation(), "Bellandur");
		assertEquals(response.getPickUpLocation(), "Ecospace");
		assertEquals(response.getFare(), 52);
		assertEquals(response.getRideStatus(), "Booked");
	}
	
	@Test
	public void testConstructorForUserResponse() {
		RidesResponse response = new RidesResponse();
		assertNotNull(response);
	}
	
	@Test
	public void testToStringForUserResponse() {
		RidesResponse response = new RidesResponse(1001, "Ecospace", "Bellandur", driver1, "Booked", 52);
		String test = response.toString();
		assertEquals(test, response.toString());
	}
	
	@Test
	public void testEqualsAndHashCodeForRide() {
		Ride ride1 = new Ride(10, 1001, 1001, "Ecospace", "Bellandur", "Micro", new Date(), new Date(), 52, "Booked");
		Ride ride2 = new Ride(10, 1001, 1001, "Ecospace", "Bellandur", "Micro", new Date(), new Date(), 52, "Booked");
		Ride ride3 = new Ride();
		
		assertTrue(ride1.equals(ride2));
		assertFalse(ride1.equals(ride3));
		
		assertEquals(ride1.hashCode(), ride2.hashCode());
		assertNotEquals(ride1.hashCode(), ride3.hashCode());
	}
	
	@Test
	public void testGettersAndSetterForRide() {
		Date date = new Date();
		Ride ride = new Ride();
		ride.setRideId(10);
		ride.setDriverId(1001);
		ride.setUserId(1001);
		ride.setPickUpLocation("Bellandur");
		ride.setDropLocation("Ecospace");
		ride.setCarType("Micro");
		ride.setBookingDate(date);
		ride.setEndTime(date);
		ride.setFare(52);
		ride.setRideStatus("Booked");
		
		assertEquals(ride.getRideId(), 10);
		assertEquals(ride.getUserId(), 1001);
		assertEquals(ride.getDriverId(), 1001);
		assertEquals(ride.getPickUpLocation(), "Bellandur");
		assertEquals(ride.getDropLocation(), "Ecospace");
		assertEquals(ride.getCarType(), "Micro");
		assertEquals(ride.getBookingDate(), date);
		assertEquals(ride.getEndTime(), date);
		assertEquals(ride.getFare(), 52);
		assertEquals(ride.getRideStatus(), "Booked");
	}
	
	@Test
	public void testConstructorForRide() {
		Ride ride = new Ride();
		assertNotNull(ride);
	}
	
	@Test
	public void testToStringForRide() {
		Ride ride = new Ride(10, 1001, 1001, "Ecospace", "Bellandur", "Micro", new Date(), new Date(), 52, "Booked");
		String test = ride.toString();
		assertEquals(test, ride.toString());
	}
	
	@Test
	public void testEqualsAndHashCodeForUser() {
		User user1 = new User("Aman", 1235656567);
		User user2 = new User("Aman", 1235656567);
		User user3 = new User();
		
		assertTrue(user1.equals(user2));
		assertFalse(user2.equals(user3));
		
		assertEquals(user1.hashCode(), user2.hashCode());
		assertNotEquals(user1.hashCode(), user3.hashCode());
	}
	
	@Test
	public void testGettersAndSetterForUser() {
		User user = new User();
		user.setName("Aman");
		user.setPhone(1235656567);
		
		assertEquals(user.getName(), "Aman");
		assertEquals(user.getPhone(), 1235656567);
	}
	
	@Test
	public void testConstructorForUser() {
		User user = new User();
		assertNotNull(user);
	}
	
	@Test
	public void testToStringForUser() {
		User user = new User("Aman", 1235656567);
		String test = user.toString();
		assertEquals(test, user.toString());
	}

	@Test
	public void testEqualsAndHashCodeForDriver() {
		Driver driver1 = new Driver(101, "Kishan", 9878656710L, car1);
		Driver driver2 = new Driver(101, "Kishan", 9878656710L, car1);
		Driver driver3 = new Driver();
		
		assertTrue(driver1.equals(driver2));
		assertTrue(driver2.equals(driver1));
		assertFalse(driver3.equals(driver2));
		assertFalse(driver3.equals(driver1));
		
		assertEquals(driver1.hashCode(), driver2.hashCode());
		assertNotEquals(driver3.hashCode(), driver2.hashCode());
	
	}
	
	@Test
	public void testGettersAndSetterForDriver() {
		Driver driver = new Driver();
		driver.setDriverId(1001);
		driver.setName("Kishan");
		driver.setCar(car1);
		
		assertEquals(driver.getDriverId(), 1001);
		assertEquals(driver.getName(), "Kishan");
		assertEquals(driver.getCar(), car1);
	}
	
	@Test
	public void testConstructorForDriver() {
		Driver driver = new Driver();
		assertNotNull(driver);
	}
	
	@Test
	public void testToStringForDriver() {
		Driver driver = new Driver(101, "Kishan", 9878656710L, car1);
		String test = driver.toString();
		assertEquals(test, driver.toString());
	}
	
	@Test
	public void testGettersAndSettersForCar() {
		Car car = new Car();
		car.setCarName("Tata Tiago");
		car.setCarNumber("KA09GH6752");
		car.setCarType("Micro");
		
		assertEquals(car.getCarName(), "Tata Tiago");
		assertEquals(car.getCarNumber(), "KA09GH6752");
		assertEquals(car.getCarType(), "Micro");
	}
	
	@Test
	public void testEqualsAndHashCodeForCar() {
		Car car1 = new Car("KA09GH6752", "Micro", "Tata Tiago");
		Car car2 = new Car("KA09GH6752", "Micro", "Tata Tiago");
		Car car3 = new Car();
		
		assertTrue(car1.equals(car2));
		assertFalse(car2.equals(car3));
		
		assertEquals(car1.hashCode(), car2.hashCode());
		assertNotEquals(car2.hashCode(), car3.hashCode());
	}
	
	@Test
	public void testToStringForCar() {
		Car car = new Car("KA09GH6752", "Micro", "Tata Tiago");
		String test = car.toString();
		assertEquals(test, car.toString());
	}
	
	@Test
	public void testConstructorForCar() {
		Car car = new Car();
		assertNotNull(car);
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
	public void testConstructorForSeq() {
		Sequence testSeq = new Sequence();
		assertNotNull(testSeq);
	}
	
	@Test
	public void testSeqService() {
		long value = seqService.getNextSequence("Test");
		assertEquals(value, 101);
	}
	
	@Test
	public void testToSringForExResponse() {
		LocalDate date = LocalDate.now();
		ExceptionResponse response = new ExceptionResponse(date, "msg", "msg", "msg");
		String testResponse = response.toString();
		assertEquals(testResponse, response.toString());
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
		ExceptionResponse response1 = new ExceptionResponse(date, "msg", "msg", "msg");
		ExceptionResponse response2 = new ExceptionResponse(date, "msg", "msg", "msg");
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

}
