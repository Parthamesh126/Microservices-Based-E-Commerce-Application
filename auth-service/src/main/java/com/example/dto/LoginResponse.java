package com.example.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String userId,
        String name,
        String email,
        String role
) {}
