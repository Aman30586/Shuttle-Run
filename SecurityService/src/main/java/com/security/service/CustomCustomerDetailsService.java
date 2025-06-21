package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.security.config.CustomCustomerDetails;
import com.security.entity.Credentials;
import com.security.repository.CredentialRepository;


@Component
public class CustomCustomerDetailsService implements UserDetailsService{

	@Autowired
	private CredentialRepository credentialRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
		Credentials credential = credentialRepository.findByUsername(username);
	    return new CustomCustomerDetails(credential);
	}

}
