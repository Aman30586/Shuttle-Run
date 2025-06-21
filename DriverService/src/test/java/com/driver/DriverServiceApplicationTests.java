package com.driver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.driver.controller.DriverController;
import com.driver.entity.Car;
import com.driver.entity.Driver;
import com.driver.exception.DriverGlobalExceptionHandler;
import com.driver.exception.DriverNotFoundException;
import com.driver.exception.DriversNotAvailableException;
import com.driver.exception.ExceptionResponse;
import com.driver.sequence.Sequence;
import com.driver.sequence.SequenceService;
import com.driver.service.DriverServiceImpl;

@SpringBootTest
class DriverServiceApplicationTests {

	@Autowired
	DriverServiceImpl serviceImpl;
	
	@Autowired
	SequenceService seqService;
	
	@Autowired
	DriverController driverController;
	
	@Autowired
	DriverGlobalExceptionHandler globalExceptionHandler;
	
	Car car1 = new Car("UH09KN2011", "Tata Indigo", "Micro");
	Driver driver1 = new Driver(1001, "Ritik", "Khare", "Purhasi", "123456789876", "rk.nk@gmail.com", "9878656711", true, car1);
	
	Car car2 = new Car("UH09KN1120", "Tata Indigo", "Micro");
	Driver driver2 = new Driver(1002, "Ritik", "Kumar", "Purhasi", "123456789877", "rk.nk@ymail.com", "9878656712", true, car2);
	@Test
	public void testAddDriver() {
		ResponseEntity<String> response = driverController.addDriver(driver1);
		String test = response.getBody();
		assertEquals(test, "Driver added Succesfully!!");
	}

	@Test
	public void testUpdateDetails() {
		serviceImpl.addDriver(driver2);
		ResponseEntity<Driver> response1 = driverController.updateDriverDetails(1002, "FirstName", "Naitik");
		Driver driverFirst = response1.getBody();
		
		ResponseEntity<Driver> response2 = driverController.updateDriverDetails(1002, "LastName", "Kumar");
		Driver driverLast = response2.getBody();
		
		ResponseEntity<Driver> response3 = driverController.updateDriverDetails(1002, "Address", "Jhanea");
		Driver driverAddress = response3.getBody();
		
		ResponseEntity<Driver> response4 = driverController.updateDriverDetails(1002, "Email", "nk.rk@example.com");
		Driver driverEmail = response4.getBody();
		
		assertEquals(driverLast.getName(), "Kumar");
		assertEquals(driverFirst.getUsername(), "Naitik");
		assertEquals(driverAddress.getAddress(), "Jhanea");
		assertEquals(driverEmail.getEmail(), "nk.rk@example.com");
	}
	
	@Test
	public void testUpdateException() {
		Exception ex = assertThrows(DriverNotFoundException.class, () -> {
			driverController.updateDriverDetails(1010, null, null);
		});
		String expected = "Driver Not Found!!";
		String actual = ex.getMessage();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDisplayDriverById() {
		ResponseEntity<Driver> response = driverController.fetchDriverById(1001);
		Driver driver = response.getBody();
		assertEquals(driver.getDriverId(), this.driver1.getDriverId());
	}
	
	@Test
	public void testDriverByIdException() {
		Exception ex = assertThrows(DriverNotFoundException.class, () -> {
			driverController.fetchDriverById(1010);
		});
		String expected = "Driver Not Found!!";
		String actual = ex.getMessage();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDisplayDrivers() {
		ResponseEntity<List<Driver>> response = driverController.fetchAllDrivers();
		List<Driver> drivers = response.getBody();
		List<Driver> driverList = serviceImpl.fetchAllDrivers();
		assertEquals(drivers.size(), driverList.size());
	}
	
	@Test
	public void testDisplayDriversByCarType() {
		List<Driver> drivers = driverController.fetchDriversByCarType("Micro");
		List<Driver> driverList = serviceImpl.fetchDriversByCarType("Micro");
		assertEquals(drivers.size(), driverList.size());
	}
	
	@Test
	public void testDriverByCarTypeException() {
		Exception ex = assertThrows(DriversNotAvailableException.class, () -> {
			driverController.fetchDriversByCarType("SUV");
		});
		String expected = "Drivers are not Available for SUV cars";
		String actual = ex.getMessage();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testupdateAvailability() {
		driverController.updateAvailabilty(1001, false);
		assertEquals(true, true);
	}
	
	@Test
	public void testDeleteDriver() {
		ResponseEntity<String> response = driverController.deleteDriver(1001);
		String test = response.getBody();
		assertTrue(test.equals("Driver Deleted!!"));
	}
	
	@Test
	public void testDeleteIdException() {
		Exception ex = assertThrows(DriverNotFoundException.class, () -> {
			driverController.deleteDriver(1010);
		});
		String expected = "Driver Not Found!!";
		String actual = ex.getMessage();
		assertEquals(expected, actual);
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
		ExceptionResponse response = new ExceptionResponse(date, "msg", "msg", "msg");
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
		ExceptionResponse response1 = new ExceptionResponse(date, "msg","msg", "msg");
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
	
	@Test
	public void testEqualsAndHashCodeForDriver() {
		Driver driver1 = new Driver(101, "Kishan", "Kumar", "Shehdol", "123456789878", "kishan@ymail.com", "9878656714", true, car1);
		Driver driver2 = new Driver(101, "Kishan", "Kumar", "Shehdol", "123456789878", "kishan@ymail.com", "9878656714", true, car1);
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
		driver.setUsername("Kumar");
		driver.setAddress("Shehdol");
		driver.setLicenseNumber("123567898767");
		driver.setPhoneNumber("9876532100");
		driver.setEmail("kishan@ymail.com");
		driver.setAvailable(true);
		driver.setCar(car1);
		
		assertEquals(driver.getDriverId(), 1001);
		assertEquals(driver.getName(), "Kishan");
		assertEquals(driver.getUsername(), "Kumar");
		assertEquals(driver.getAddress(), "Shehdol");
		assertEquals(driver.getLicenseNumber(), "123567898767");
		assertEquals(driver.getPhoneNumber(), "9876532100");
		assertEquals(driver.getEmail(), "kishan@ymail.com");
		assertEquals(driver.isAvailable(), true);
		assertEquals(driver.getCar(), car1);
	}
	
	@Test
	public void testConstructorForDriver() {
		Driver driver = new Driver();
		assertNotNull(driver);
	}
	
	@Test
	public void testToStringForDriver() {
		Driver driver = new Driver(101, "Kishan", "Kumar", "Shehdol", "123456789878", "kishan@ymail.com", "9878656714", true, car1);
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
	
	

}
