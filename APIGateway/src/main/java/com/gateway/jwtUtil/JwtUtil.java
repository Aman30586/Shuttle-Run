package com.gateway.jwtUtil;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	public static final String SECRET_KEY = "b36d5389056e53eb834b3c0ed739f06c56805e95d591c1c3dbb924ed9d00a410";


    @SuppressWarnings("deprecation")
	public boolean validateToken(final String token) {
    	try {
    		Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    		return true;
    	}
    	catch(Exception ex){
    		return false;
    	}
    }



    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    @SuppressWarnings("deprecation")
    public String extractRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("role");
    }
}
