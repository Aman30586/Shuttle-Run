package com.security.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@SuppressWarnings("deprecation")
@Component
public class JwtService {

    public static final String SECRET = "b36d5389056e53eb834b3c0ed739f06c56805e95d591c1c3dbb924ed9d00a410";

    public String extractRole(String token) {
		Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }

	public boolean validateToken(final String token) {
    	try {
    		Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    		return true;
    	}
    	catch(Exception e) {
    		return false;
    	}
    }

	public String generateToken(String userName, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.toString());  
        return Jwts.builder()
        		.setClaims(claims)
        		.setSubject(userName)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
        		.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
        		.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
//        CustomerResponse customerResponse = customerClient.displayByUsername(userName);
//        customerResponse.setToken(token);
//        return customerResponse;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}