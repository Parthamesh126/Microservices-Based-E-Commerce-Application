package com.example.userservice.dto;

public record AuthValidationResponse(
		
		String id,
        String name,
        String email,
        String role,
        boolean enabled
		
){}
