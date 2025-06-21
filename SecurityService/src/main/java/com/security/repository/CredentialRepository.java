package com.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.security.entity.Credentials;

@Repository
public interface CredentialRepository extends MongoRepository<Credentials, String>{
	public Credentials findByUsername(String username);
	public Credentials findByEmail(String email);
}
