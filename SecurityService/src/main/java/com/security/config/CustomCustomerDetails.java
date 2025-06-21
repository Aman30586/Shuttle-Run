package com.security.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.entity.Credentials;


public class CustomCustomerDetails implements UserDetails{

	
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private String role;

    public CustomCustomerDetails(Credentials credential) {
        super();
        this.username = credential.getUsername();
        this.password = credential.getPassword();
        this.role = credential.getRole();
    }

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public String getPassword() { 
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return username;
	}
	
	public String getRole() {
        return role;
    }


	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
