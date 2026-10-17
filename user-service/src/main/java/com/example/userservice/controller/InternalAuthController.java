package com.example.userservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.dto.AuthValidationRequest;
import com.example.userservice.dto.AuthValidationResponse;
import com.example.userservice.service.UserService;

@RestController
@RequestMapping("/internal/auth")
public class InternalAuthController {
	
	private final UserService userService;
	
	public InternalAuthController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/validate")
	public AuthValidationResponse validate(@RequestBody AuthValidationRequest request) {
		return userService.validateCredentials(request);
	}

}
