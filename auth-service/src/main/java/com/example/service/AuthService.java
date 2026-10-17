package com.example.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.client.UserAuthClient;
import com.example.dto.AuthValidationRequest;
import com.example.dto.AuthValidationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;

import feign.FeignException;

@Service
public class AuthService {
	
	private final UserAuthClient userAuthClient;
	
	private final JwtService jwtService;
	
	public AuthService(UserAuthClient userAuthClient, JwtService jwtService) {
		this.jwtService = jwtService;
		this.userAuthClient = userAuthClient;
	}

	public LoginResponse login(LoginRequest request) {
		
		AuthValidationResponse user;
		
		try {
			
			user = userAuthClient.validateCredentials(new AuthValidationRequest(request.email(), request.password()));
			
		} catch(FeignException e) {
			
			if(e.status() == 401) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or passowrd");
			}
			
			if(e.status() == 403) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is Disabled");
			}
			
			throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "User service is unavailable");
		}
		
		if(user == null || !user.enabled()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication Failed");
		}
		
		String token = jwtService.generateAccessToken(user);
		
		return new LoginResponse(
				token,
				"Bearer",
				jwtService.getExpirationMs()/100,
				user.id(),
				user.name(),
				user.email(),
				user.role());
		
	}
	
}
