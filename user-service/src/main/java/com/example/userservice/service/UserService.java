package com.example.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.userservice.dto.AuthValidationRequest;
import com.example.userservice.dto.AuthValidationResponse;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.User;

@Service
public interface UserService {
	
	public UserResponse createUser(User user);
	
	public UserResponse getuser(String id);
	
	public List<UserResponse> getAllUsers();

	AuthValidationResponse validateCredentials(AuthValidationRequest request);

}
