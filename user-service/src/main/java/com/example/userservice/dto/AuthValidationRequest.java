package com.example.userservice.dto;

public record AuthValidationRequest(
		
        String email,
        String password
        
) {}
