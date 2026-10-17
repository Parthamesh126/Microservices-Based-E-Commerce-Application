package com.example.dto;

public record AuthValidationRequest(
        String email,
        String password
) {}
