package com.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.gateway.exception.AuthorizationHeaderException;
import com.gateway.exception.TokenIsNotValidException;
import com.gateway.exception.UnauthorizedAccessException;
import com.gateway.jwtUtil.JwtUtil;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new AuthorizationHeaderException("Please provide authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                if(!jwtUtil.validateToken(authHeader)) {
                	throw new TokenIsNotValidException("Token isn't valid!!");
                }

                String role = jwtUtil.extractRole(authHeader); // Extract role from token
                String path = exchange.getRequest().getURI().getPath();
                if (!checkRoleAccess(role, path)) {
                    throw new UnauthorizedAccessException("Unauthorized access by " + role);
                }
            }
            return chain.filter(exchange);
        });
    }
    
    private boolean checkRoleAccess(String role, String path) {
        if (role.equals("ADMIN")) {
            return true;
        } 
        else if (role.equals("USER")) {
            return path.startsWith("/user/update") || path.startsWith("/user/display/") || path.startsWith("/user/deleteUser/") || path.startsWith("/ride/cancelRide/") || path.startsWith("/ride/displayRidesByUserId") || path.startsWith("/ride/bookride/") || path.startsWith("/ride/getFare/") || path.startsWith("/user/deleteUser/");
        }
        else if (role.equals("DRIVER")){
        	return path.startsWith("/ride/displayRidesByDriverId/") || path.startsWith("/ride/endRide/") || path.startsWith("/driver/updateDetails/") || path.startsWith("/driver/display/");
        }
        return false;
    }

    public static class Config {

    }
}
