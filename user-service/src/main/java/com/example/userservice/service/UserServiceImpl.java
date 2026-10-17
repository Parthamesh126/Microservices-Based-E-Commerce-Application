package com.example.userservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.userservice.dto.AuthValidationRequest;
import com.example.userservice.dto.AuthValidationResponse;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.enums.Roles;
import com.example.userservice.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final UserRepository repo;
	
	private final PasswordEncoder passwordEncoder;
	
	UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
		this.repo = userRepository; 
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponse createUser(User user) {
		
		if (user == null
                || user.getName() == null
                || user.getName().isBlank()
                || user.getEmail() == null
                || user.getEmail().isBlank()
                || user.getPassword() == null
                || user.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Name, email and password are required");
        }
		
		String email = user.getEmail().trim().toLowerCase();
		
		if(repo.existsByEmailIgnoreCase(email)) {
			throw new IllegalArgumentException("Email already registered.");
		}
		
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if(user.getRole() == null) {
			user.setRole(Roles.USER);
		}
		
		user.setEnabled(true);
		
		User savedUser = repo.save(user);

        log.info("User created successfully");

        return toResponse(savedUser);	
	}

	@Override
	public UserResponse getuser(String id) {
		
		if(id == null || id.isEmpty() || id.isBlank()) return null;
		
		return repo.findById(id).map(this::toResponse).orElse(null);
	}

	@Override
	public List<UserResponse> getAllUsers() {
				
		return repo.findAll().stream().map(this::toResponse).toList();
	}
	

	private UserResponse toResponse(User user) {
	    return new UserResponse(
	        user.getId(),
	        user.getName(),
	        user.getEmail(),
	        user.getRole().toString()
	    );
	}

	@Override
	public AuthValidationResponse validateCredentials(AuthValidationRequest request) {
		
		if(request == null || request.email() == null || request.password() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		User user = repo.findByEmailIgnoreCase(request.email().trim())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		
		if(!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		
		if(!user.isEnabled()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		return new AuthValidationResponse(
				user.getId(), 
				user.getName(), 
				user.getEmail(), 
				user.getRole().toString(), 
				user.isEnabled());
	}

}
