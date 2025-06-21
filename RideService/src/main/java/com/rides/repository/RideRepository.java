package com.rides.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rides.entity.Ride;

@Repository
public interface RideRepository extends MongoRepository<Ride, Long>{

	public List<Ride> findByUserId(long userId);
	public List<Ride> findByDriverId(long driverId);
}
